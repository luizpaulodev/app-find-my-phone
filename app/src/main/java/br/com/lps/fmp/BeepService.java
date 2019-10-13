package br.com.lps.fmp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.AudioManager;

import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import br.com.lps.fmp.model.PreferenciasAtivar;
import br.com.lps.fmp.util.NotificationUtils;

/**
 * Created by Luiz Paulo Oliveira on 18/02/2017.
 */

public class BeepService extends Service {


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();



        //NotificationUtils.criarNotificacaoCompleta(this, "Testo Parametro", 10);

        /*

        MediaPlayer mp = MediaPlayer.create(this, R.raw.beep);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {

                mp.release();
            }

        });
        mp.setLooping(true);
        mp.start();
        */
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
