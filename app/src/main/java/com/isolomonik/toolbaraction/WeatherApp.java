package com.isolomonik.toolbaraction;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.isolomonik.toolbaraction.services.NotificationService;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by ira on 30.12.15.
 */
public class WeatherApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(config);

        startService(new Intent(this.getApplicationContext(), NotificationService.class));
    }
}