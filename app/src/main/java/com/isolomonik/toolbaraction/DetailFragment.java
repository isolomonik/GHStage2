package com.isolomonik.toolbaraction;




import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


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

        TextView dateTV = (TextView) view.findViewById(R.id.dmy_detail);
       // TextView hourTV = (TextView) view.findViewById(R.id.hour_detail);
        ImageView iconIV = (ImageView) view.findViewById(R.id.icon_detail);
        TextView tempTV = (TextView) view.findViewById(R.id.temp_detail);
        TextView mainTV = (TextView) view.findViewById(R.id.main_detail);
        TextView windTV = (TextView) view.findViewById(R.id.wind_detail);
       // TextView windDegTV = (TextView) view.findViewById(R.id.wind_deg_detail);
        TextView humidityTV = (TextView) view.findViewById(R.id.humidity_detail);

        HourlyWeather hourWeather = MainListDetailActivity.weather.get(position);

//     //   dateTextView.setText(String.format(getResources().getString(R.string.dd_mmm_yyyy), aForecast.get("day"), aForecast.get("month"), aForecast.get("year")));
        dateTV.setText(hourWeather.dt_txt);
        tempTV.setText(String.format("%.1f \u2103 ",hourWeather.main.temp));
        mainTV.setText(hourWeather.weather[0].description);
       // windTV.setText(Double.toString(hourWeather.wind.speed));
        windTV.setText(String.format("Wind: %.2f m/s %d", hourWeather.wind.speed, hourWeather.wind.deg));
      //  windDegTV.setText(hourWeather.wind.deg);
        humidityTV.setText(String.format("Humidity: %d %%",hourWeather.main.humidity));
        Picasso.with(getContext()).load("http://openweathermap.org/img/w/" + hourWeather.weather[0].icon + ".png").into(iconIV);
    }


}
