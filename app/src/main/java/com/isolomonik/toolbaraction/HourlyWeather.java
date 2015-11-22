package com.isolomonik.toolbaraction;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ira on 21.11.15.
 */
public class HourlyWeather {


    public String dt;
    public Main main;
    public Weather[] weather;
    public Clouds clouds;
    public Wind wind;
    public Rain rain;
    public String dt_txt;

    public class Main {
        public double temp;
        public double pressure;
        public int humidity;

    }

    public class Clouds {
        public int all;
    }

    public class Weather {
        public int id;
        public String main;
        public String description;
        public String icon;
    }

    public class Wind {
        public double speed;
        public int deg;
    }

    public class Rain {
        public double h3;
    }
}