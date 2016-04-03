package com.isolomonik.toolbaraction.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ira on 30.12.15.
 */
public class GlobalVar {

    public static final String MY_LOG = "Weather";

    public static long scTime=60000*4;   // 4 min

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }


}
