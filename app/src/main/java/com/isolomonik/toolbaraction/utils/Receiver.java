package com.isolomonik.toolbaraction.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.isolomonik.toolbaraction.services.NotificationService;

/**
 * Created by irina on 03.04.2016.
 */
public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
       // Log.v(GlobalVar.MY_LOG, "receive "+intent.getAction().toString());
        Intent i=new Intent(context, NotificationService.class);
        context.startService(i);

    }
}
