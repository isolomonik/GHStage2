package com.isolomonik.toolbaraction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by ira on 22.11.15.
 */
public class WeatherAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<WeatherData> weather;

    private static class ViewHolder {
        ImageView imgv;
        TextView city;
        TextView description;
        TextView temperature;
    }

    public WeatherAdapter(Context context, ArrayList<WeatherData> weather) {
        this.context = context;
        this.weather =weather;


    }

    @Override
    public int getCount() {
        return weather.size();
    }

    @Override
    public Object getItem(int position) {
        return weather.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      //  ViewHolder vh = null;
        WeatherData forecast = weather.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_weather, parent, false);

        Picasso.with(context).load("http://openweathermap.org/img/w/" + forecast.getIcon() + ".png").into((ImageView) rowView.findViewById(R.id.img));

        ((TextView) rowView.findViewById(R.id.dmy)).setText(forecast.getDate());
        ((TextView) rowView.findViewById(R.id.description)).setText(forecast.getDescription());
        ((TextView) rowView.findViewById(R.id.temp)).setText(String.format("%.1f \u2103 ", forecast.getTemp()));

        return rowView;
    }
}
