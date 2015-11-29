package com.isolomonik.toolbaraction;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by ira on 21.11.15.
 */
public interface CallBackInterface {

    void updateContent(int position);
   // List<HourlyWeather> getweatherList();
   ArrayList<WeatherData> getweatherList();
}
