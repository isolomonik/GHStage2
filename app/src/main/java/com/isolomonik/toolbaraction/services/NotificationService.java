package com.isolomonik.toolbaraction.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.TimeUtils;

import com.isolomonik.toolbaraction.R;
import com.isolomonik.toolbaraction.activities.MainListDetailActivity;
import com.isolomonik.toolbaraction.models.WeatherData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;

public class NotificationService extends Service {

    NotificationManager nm;
    Notification notification;
    Notification.Builder builder;
private static final int NOTIFY_ID=301;


    public NotificationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Context context=getApplicationContext();
        Intent notificationIntent=new Intent(context, MainListDetailActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        Resources res=context.getResources();
        builder= new Notification.Builder(context);

        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.mipmap.ic_weather)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_10d))
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_ALL)
                        //  .setAutoCancel(true)
                        //  .addAction()
                .setContentText(res.getText(R.string.notificationText))
                .setTicker(res.getString(R.string.notificationText));

        // Notification notification = builder.getNotification(); // до API 16
        notification = builder.build();

//        NotificationManager notificationManager = (NotificationManager) context
//                .getSystemService(Context.NOTIFICATION_SERVICE);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        //   nm.notify(NOTIFY_ID, notification);
        Thread thread = new Thread(){
            @Override
            public void run() {
                while (true) {
                    nm.notify(NOTIFY_ID, notification);
                    try {
                        TimeUnit.SECONDS.sleep(60);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
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
