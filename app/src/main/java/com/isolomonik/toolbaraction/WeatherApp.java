package com.isolomonik.toolbaraction;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.isolomonik.toolbaraction.services.NotificationService;
import com.isolomonik.toolbaraction.utils.GlobalVar;
import com.isolomonik.toolbaraction.utils.Receiver;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class WeatherApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(config);
        new GlobalVar();

        Intent alarmIntent= new Intent(this, Receiver.class);
        PendingIntent pendingIntent= PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC, System.currentTimeMillis()+ GlobalVar.scTime, pendingIntent);

        //startService(new Intent(this.getApplicationContext(), NotificationService.class));
    }
}