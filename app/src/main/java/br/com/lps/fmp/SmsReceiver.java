package br.com.lps.fmp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsMessage;
import android.util.Log;

import br.com.lps.fmp.model.PreferenciasAtivar;

/**
 * Created by Luiz Paulo Oliveira on 18/02/2017.
 */

public class SmsReceiver extends BroadcastReceiver {

    private SharedPreferences preferences;
    private PreferenciasAtivar preferenciasAtivar;
    private AudioManager audioManager = null;


    @Override
    public void onReceive(Context context, Intent intent) {


        switch (intent.getAction()){

            case "android.provider.Telephony.SMS_RECEIVED":
                SmsMessage sms = getMessagesFromIntent(intent)[0];

                String telefone = sms.getOriginatingAddress();
                String mensagem = sms.getMessageBody();
                String palavraChave;

                SharedPreferences preferences = context.getSharedPreferences("MyPhone", Context.MODE_PRIVATE);
                palavraChave = preferences.getString(PreferenciasAtivar.PALAVRA_CHAVE, "");

                if(palavraChave != ""){
                    if(mensagem.equalsIgnoreCase(palavraChave)){

                        ativarRecursos(context);

                        //context.startService(new Intent(context, BeepService.class));
                    }
                }
                break;

            case "FMP_KEY_NOTIFICACAO":
                //context.stopService(new Intent(context, BeepService.class));
                break;
        }
    }

    public static SmsMessage[] getMessagesFromIntent(Intent intent){

        Object[] pdusExtras = (Object[]) intent.getSerializableExtra("pdus");

        SmsMessage[] messages = new SmsMessage[pdusExtras.length];

        for(int i = 0; i < pdusExtras.length; i++){
            messages[i] = SmsMessage.createFromPdu((byte[]) pdusExtras[i]);
        }

        return messages;
    }

    private void ativarRecursos(Context context){

        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        preferences = context.getSharedPreferences("MyPhone", context.MODE_PRIVATE);

        preferenciasAtivar = new PreferenciasAtivar();
        preferenciasAtivar.setToqueChamadas(preferences.getBoolean(PreferenciasAtivar.CHAMADAS, false));
        preferenciasAtivar.setSomNotificacao(preferences.getBoolean(PreferenciasAtivar.NOTIFICACAO, false));
        preferenciasAtivar.setSonsMultimidias(preferences.getBoolean(PreferenciasAtivar.MULTIMIDIA, false));
        preferenciasAtivar.setDadosMoveis(preferences.getBoolean(PreferenciasAtivar.DADOS_MOVEIS, false));
        preferenciasAtivar.setWireless(preferences.getBoolean(PreferenciasAtivar.WIRELESS, false));
        preferenciasAtivar.setLanterna(preferences.getBoolean(preferenciasAtivar.LANTERNA, false));

        if(preferenciasAtivar.isToqueChamadas()){
            audioManager.setStreamVolume(AudioManager.STREAM_RING, 100, 0);
        }

        if(preferenciasAtivar.isSonsMultimidias()){
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 100, 0);
            audioManager.setStreamVolume(AudioManager.STREAM_ALARM, 100, 0);

        }else {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
            audioManager.setStreamVolume(AudioManager.STREAM_ALARM, 0, 0);
            audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, 0, 0);
        }

        if (preferenciasAtivar.isSomNotificacao()){
            audioManager.setStreamVolume(AudioManager.STREAM_RING, 1, 0);
            audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 100, 0);
            audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, 100, 0);
        } else {
            audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 0, 0);
        }

        if(preferenciasAtivar.isLanterna() && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            Camera camera;

            PackageManager pm = context.getPackageManager();

            if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {

                camera = Camera.open();
                final Camera.Parameters p = camera.getParameters();

                p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(p);
            }
        }

        if(preferenciasAtivar.isWireless()){
            WifiManager wifi = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
            wifi.setWifiEnabled(true);
        }
    }
}
