package com.isolomonik.toolbaraction;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by ira on 21.11.15.
 */
public class HourlyWeather  {


    private String dt;
    private Main main;
    private Weather[] weather;

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }

    private Clouds clouds;
    private Wind wind;
    private Rain rain;
    private String dt_txt;

    private class Main {
        public double temp;
        public double pressure;
        public int humidity;

    }

    private class Clouds  {
        public int all;
    }

    private class Weather {
        public int id;
        public String main;
        public String description;
        public String icon;
    }

    private class Wind {
        public double speed;
        public int deg;
    }

    private class Rain{
        public double h3;
    }
}