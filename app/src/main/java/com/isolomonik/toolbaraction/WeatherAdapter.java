package com.isolomonik.toolbaraction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by ira on 22.11.15.
 */
public class WeatherAdapter extends BaseAdapter {

    private Context context;

    public WeatherAdapter(Context context) {
        this.context = context;

    }

    @Override
    public int getCount() {
        return MainListDetailActivity.weather.size();
    }

    @Override
    public Object getItem(int position) {
        return MainListDetailActivity.weather.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HourlyWeather forecast = MainListDetailActivity.weather.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_weather, parent, false);

        Picasso.with(context).load("http://openweathermap.org/img/w/" + forecast.weather[0].icon + ".png").into((ImageView) rowView.findViewById(R.id.img));

        ((TextView) rowView.findViewById(R.id.dmy)).setText(forecast.dt_txt);
        ((TextView) rowView.findViewById(R.id.description)).setText(forecast.weather[0].description);
        ((TextView) rowView.findViewById(R.id.temp)).setText(String.format("%.1f \u2103 ", forecast.main.temp));

        return rowView;
    }
}
