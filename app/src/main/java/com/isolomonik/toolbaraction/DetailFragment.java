package com.isolomonik.toolbaraction;




import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;


public class DetailFragment extends Fragment {

    private int position;


    public void setItemContent(int position) {
        this.position = position;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_detail, container, false);
    }


    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView weekTV = (TextView) view.findViewById(R.id.week_detail);
        TextView dateTV = (TextView) view.findViewById(R.id.dmy_detail);
        TextView hourTV = (TextView) view.findViewById(R.id.hour_detail);
        ImageView iconIV = (ImageView) view.findViewById(R.id.icon_detail);
        TextView mainTV = (TextView) view.findViewById(R.id.main_detail);
        TextView windTV = (TextView) view.findViewById(R.id.wind_detail);
        TextView windDegTV = (TextView) view.findViewById(R.id.wind_deg_detail);
        TextView humidityTV = (TextView) view.findViewById(R.id.humidity_detail);
        // Set data to TextViews
        HashMap<String, String> hourWeather = ListViewFragment.WeatherData.get(position);
        weekTV.setText(hourWeather.get("week"));
     //   dateTextView.setText(String.format(getResources().getString(R.string.dd_mmm_yyyy), aForecast.get("day"), aForecast.get("month"), aForecast.get("year")));
        hourTV.setText(hourWeather.get("hour"));
        mainTV.setText(hourWeather.get("main"));
        windTV.setText(hourWeather.get("wind"));
        windDegTV.setText(hourWeather.get("direction"));
        humidityTV.setText(hourWeather.get("humidity"));
        // Set image
//        Picasso.with(getContext())
//                .load(String.format(getResources().getString(R.string.icon_url), aForecast.get("icon")))
//                .into(iconImageView);
    }


}
