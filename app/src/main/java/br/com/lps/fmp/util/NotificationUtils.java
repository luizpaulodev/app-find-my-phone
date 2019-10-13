package br.com.lps.fmp.util;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat;

import br.com.lps.fmp.MainActivity;
import br.com.lps.fmp.R;

/**
 * Created by Luiz Paulo Oliveira on 03/04/2017.
 */

public class NotificationUtils {
    public static final String KEY_NOTIFICACAO = "KEY_NOTIFICACAO";
    public static final String ACAO_ENCERRAR = "ENCERRAR_BUSCA";
    public static final String ACAO_NOTIFICACAO = "FMP_KEY_NOTIFICACAO";

    private static PendingIntent getPendingIntent(Context context, String texto, int id){

        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(KEY_NOTIFICACAO, texto);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);

        return stackBuilder.getPendingIntent(id, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static void criarNotificacaoCompleta(Context context, String texto, int id){

        PendingIntent pitAcao = PendingIntent.getBroadcast(context, 0, new Intent(ACAO_NOTIFICACAO), 0);
        PendingIntent pitEncerrar = PendingIntent.getBroadcast(context, 0, new Intent(ACAO_ENCERRAR), 0);
        PendingIntent pitNotificacao = getPendingIntent(context, texto, id);

        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setContentTitle("Localizar Celular")
                //.setContentText("Text")
                //.setTicker("Chegou notificação")
                .setWhen(System.currentTimeMillis())
                .setLargeIcon(largeIcon)
                .setAutoCancel(false)
                .setContentIntent(pitNotificacao)
                .setDeleteIntent(pitEncerrar)
                .setLights(Color.BLUE, 1000, 5000)
                //.setSound()
                .setVibrate(new long[]{100, 500, 200, 800})
                .addAction(R.drawable.ic_close_black_24dp, "Encerrar", pitAcao);
                //.setNumber(id);
                //.setSubText("SubTexto");

        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
        nm.notify(id, mBuilder.build());
    }
}
