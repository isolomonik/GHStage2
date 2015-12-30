package com.isolomonik.toolbaraction.models;

import java.util.ArrayList;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ira on 28.11.15.
 */
public class WeatherData extends RealmObject {
    @PrimaryKey
    private  String dt;

    private double temp;
    private double pressure;
    private int humidity;
    private String description;
    private String date;
    private String icon;
    private double speed;
    private int deg;

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getDate() {

        return date;
    }

    public void setDate(String dt_txt) {
        this.date = dt_txt;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }
}
