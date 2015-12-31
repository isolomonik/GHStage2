package com.isolomonik.toolbaraction.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.NotificationCompat;
import android.graphics.BitmapFactory;
import android.os.IBinder;


import com.isolomonik.toolbaraction.R;
import com.isolomonik.toolbaraction.activities.MainListDetailActivity;
import com.isolomonik.toolbaraction.utils.GlobalVar;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class NotificationService extends Service {

    NotificationManager nm;
    Notification notification;
    Notification.Builder builder;
    private static final int NOTIFY_ID = 301;


    public NotificationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Context context = getApplicationContext();
        Intent notificationIntent = new Intent(context, MainListDetailActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Resources res = context.getResources();

        Intent intentDownload = new Intent(this, LoadDataService.class);
        PendingIntent downloadIntent = PendingIntent.getService(this, 0, intentDownload, PendingIntent.FLAG_UPDATE_CURRENT);
        builder = new Notification.Builder(context);

        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.mipmap.ic_weather)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_10d))
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .addAction(android.R.drawable.stat_sys_upload, "Update", downloadIntent)
                .setContentText(res.getText(R.string.notificationText))
                .setContentTitle("Update weather")
                .setTicker(res.getString(R.string.notificationText));

        // Notification notification = builder.getNotification(); // до API 16
        notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;


        Thread thread = new Thread() {
            @Override
            public void run() {
//
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    synchronized public void run() {
                        if (GlobalVar.isNetworkAvailable(getBaseContext())) {
                            builder.setWhen(System.currentTimeMillis());
                            nm.notify(NOTIFY_ID, notification);
                        }
                    }
                }, 5 * 1000, 60 * 1000);
            }
        };
        thread.start();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
