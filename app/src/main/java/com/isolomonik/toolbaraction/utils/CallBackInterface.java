package com.isolomonik.toolbaraction.utils;

import com.isolomonik.toolbaraction.models.WeatherData;

import java.util.ArrayList;

/**
 * Created by ira on 21.11.15.
 */
public interface CallBackInterface {

    void updateContent(int position);
   // List<HourlyWeather> getweatherList();
   ArrayList<WeatherData> getweatherList();
}
