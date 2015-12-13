package com.isolomonik.toolbaraction.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.isolomonik.toolbaraction.activities.MainListDetailActivity;

import java.util.concurrent.TimeUnit;

/**
 * Created by ira on 13.12.15.
 */
public class LoadDataService extends IntentService {


    NotificationManager nm;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public LoadDataService(String name) {
        super(name);
    }


    @Override

    public void onCreate() {

        super.onCreate();

        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

    }



    @Override

    public int onStartCommand(Intent intent, int flags, int startId) {

        try {

            TimeUnit.SECONDS.sleep(60);

        } catch (InterruptedException e) {

            e.printStackTrace();

        }

        sendNotif();

        return super.onStartCommand(intent, flags, startId);

    }



    void sendNotif() {

        // 1-я часть

      //  Notification notif = new Notification(R.drawable.ic_launcher, "Text in status bar", System.currentTimeMillis());



        // 3-я часть

        Intent intent = new Intent(this, MainListDetailActivity.class);

        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);



        // 2-я часть

     //   notif.setLatestEventInfo(this, "Notification's title", "Notification's text", pIntent);



        // ставим флаг, чтобы уведомление пропало после нажатия

    //    notif.flags |= Notification.FLAG_AUTO_CANCEL;

    //    notif.number = 3;



        // отправляем

     //   nm.notify(1, notif);

    }



    @Override

    public IBinder onBind(Intent arg0) {

        return null;

    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

}
