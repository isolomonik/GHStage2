package com.isolomonik.toolbaraction.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.isolomonik.toolbaraction.activities.MainListDetailActivity;
import com.isolomonik.toolbaraction.R;
import com.isolomonik.toolbaraction.models.WeatherData;
import com.isolomonik.toolbaraction.utils.GlobalVar;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


public class DetailFragment extends Fragment {
    //HourlyWeather hourWeather;
    private WeatherData weatherData;
    private int position;

    private TextView dateTV;
    private TextView hourTV;
    private ImageView iconIV;
    private TextView tempTV;
    private TextView mainTV;
    private TextView windTV;
    private TextView windDegTV;
    private  TextView humidityTV;
Realm realm;

    public void setItemContent(int position) {
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        realm.getInstance(getContext());
        return inflater.inflate(R.layout.fragment_detail, container, false);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        realm = Realm.getInstance(context);


    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


         dateTV = (TextView) view.findViewById(R.id.dmy_detail);
       //  hourTV = (TextView) view.findViewById(R.id.hour_detail);
         iconIV = (ImageView) view.findViewById(R.id.ic_big);
         tempTV = (TextView) view.findViewById(R.id.temp_detail);
         mainTV = (TextView) view.findViewById(R.id.main_detail);
         windTV = (TextView) view.findViewById(R.id.wind_detail);
       //  windDegTV = (TextView) view.findViewById(R.id.wind_deg_detail);
         humidityTV = (TextView) view.findViewById(R.id.humidity_detail);

       updateDetail();
    }


    public void updateDetail() {
             //  hourWeather =weather.get(position);
        Realm realm = Realm.getInstance(GlobalVar.realmConfiguration);
        realm.beginTransaction();
        RealmResults<WeatherData> result = realm.where(WeatherData.class).findAll();
        ArrayList<WeatherData> weatherList =new ArrayList<>();
        weatherList.addAll(result.subList(0, result.size()));
        realm.commitTransaction();
        weatherData=weatherList.get(position);

//     //   dateTextView.setText(String.format(getResources().getString(R.string.dd_mmm_yyyy), aForecast.get("day"), aForecast.get("month"), aForecast.get("year")));
        dateTV.setText(weatherData.getDate());
        tempTV.setText(String.format("%.1f \u2103 ", weatherData.getTemp()));
        mainTV.setText(weatherData.getDescription());
        // windTV.setText(Double.toString(hourWeather.wind.speed));
        windTV.setText(String.format("Wind: %.2f m/s %d", weatherData.getSpeed(), weatherData.getDeg()));
        //  windDegTV.setText(hourWeather.wind.deg);
        humidityTV.setText(String.format("Humidity: %d %%", weatherData.getHumidity()));
       // Picasso.with(getContext()).load("http://openweathermap.org/img/w/" + weatherData.getIcon() + ".png").into(iconIV);
      // int res= R.drawable.class.getField(weatherData.getIcon())..getInt();
        int resID = getResources().getIdentifier("im_"+weatherData.getIcon() , "drawable", getActivity().getPackageName());
        iconIV.setImageResource(resID);

    }
}
