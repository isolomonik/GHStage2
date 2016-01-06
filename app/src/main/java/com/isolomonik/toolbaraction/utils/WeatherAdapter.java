package com.isolomonik.toolbaraction.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import com.isolomonik.toolbaraction.R;
import com.isolomonik.toolbaraction.models.WeatherData;

import java.util.ArrayList;


/**
 * Created by ira on 22.11.15.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ForecastHolder> {


    private Context context;
    private ArrayList<WeatherData> weather;

    class ForecastHolder extends RecyclerView.ViewHolder {

        ImageView imgv;
        TextView dmy;
        TextView description;
        TextView temperature;

        public ForecastHolder(View itemView) {
            super(itemView);
            this.imgv = (ImageView) itemView.findViewById(R.id.img);
            this.dmy = (TextView) itemView.findViewById(R.id.dmy);
            this.description = (TextView) itemView.findViewById(R.id.description);
            this.temperature = (TextView) itemView.findViewById(R.id.temp);
        }
    }

    public WeatherAdapter(Context context, ArrayList<WeatherData> weather) {
        this.context = context;
        this.weather = weather;
    }


    public ForecastHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rowView = inflater.inflate(R.layout.row_weather, parent, false);

        final ForecastHolder forecastHolder = new ForecastHolder(rowView);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallBackInterface myInterface = (CallBackInterface) context;
                myInterface.updateContent(forecastHolder.getAdapterPosition());
            }
        });
        return forecastHolder;
    }

    public void onBindViewHolder(ForecastHolder holder, int position) {
        WeatherData forecast = weather.get(position);
        if (forecast != null) {

            int resID = context.getResources().getIdentifier("ic_" + forecast.getIcon(), "drawable", context.getPackageName());
            holder.imgv.setImageResource(resID);
            holder.dmy.setText(forecast.getDate());
            holder.description.setText(forecast.getDescription());
            holder.temperature.setText(String.format("%.1f \u2103 ", forecast.getTemp()));
        }
    }

    @Override
    public int getItemCount() {
        return weather.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}