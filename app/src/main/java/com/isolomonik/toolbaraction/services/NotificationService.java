package com.isolomonik.toolbaraction.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.app.NotificationCompat;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;


import com.isolomonik.toolbaraction.R;
import com.isolomonik.toolbaraction.activities.MainListDetailActivity;
import com.isolomonik.toolbaraction.utils.GlobalVar;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class NotificationService extends IntentService {

    NotificationManager nm;
    Notification notification;
    Notification.Builder builder;
    private static final int NOTIFY_ID = 301;


    public NotificationService() {
        super("NotificationService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
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
               // .setSmallIcon(R.mipmap.ic_weather)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_10d))
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setContentText(res.getText(R.string.notificationText))
                .setContentTitle("Update weather")
                .setTicker(res.getString(R.string.notificationText));

        if (Build.VERSION.SDK_INT < 16) {
            notification = builder.getNotification();
        } else {
            builder.addAction(android.R.drawable.stat_sys_upload, "Update", downloadIntent);
            notification = builder.build();
            notification.flags = Notification.FLAG_AUTO_CANCEL;
        }

        nm.notify(NOTIFY_ID, notification);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
