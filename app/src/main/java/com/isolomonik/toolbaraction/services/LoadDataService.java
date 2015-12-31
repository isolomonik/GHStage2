package com.isolomonik.toolbaraction.services;


import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


import com.isolomonik.toolbaraction.R;
import com.isolomonik.toolbaraction.models.WeatherData;
import com.isolomonik.toolbaraction.utils.GlobalVar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import io.realm.Realm;

/**
 * Created by ira on 13.12.15.
 */
public class LoadDataService extends IntentService {

    public LoadDataService() {
        super("LoadDataService");
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Realm realm;
        String json = "";
        String query = null;
        try {
            query = getResources().getString(R.string.queryWeather)
                    + "&units=metric&appid="
                    //+ URLEncoder.encode(cityName, "UTF-8")
                    + getResources().getString(R.string.dummyAppid);

            URL searchURL = new URL(query);

            HttpURLConnection httpURLConnection = (HttpURLConnection) searchURL.openConnection();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader, 8192);

                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    json += line;
                }

                bufferedReader.close();
            }

            JSONObject jsonWeather = new JSONObject(json);

            JSONArray weatherArray = jsonWeather.getJSONArray("list");

            realm = Realm.getDefaultInstance();
            realm.beginTransaction();


            for (int i = 0; i < weatherArray.length(); i++) {
                WeatherData weather = new WeatherData();
                weather.setDt(weatherArray.getJSONObject(i).getString("dt"));
                weather.setDate(weatherArray.getJSONObject(i).getString("dt_txt"));
                weather.setIcon(weatherArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("icon"));
                weather.setTemp(weatherArray.getJSONObject(i).getJSONObject("main").getDouble("temp"));
                weather.setDescription(weatherArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description"));
                weather.setDeg(weatherArray.getJSONObject(i).getJSONObject("wind").getInt("deg"));
                weather.setSpeed(weatherArray.getJSONObject(i).getJSONObject("wind").getDouble("speed"));
                weather.setHumidity(weatherArray.getJSONObject(i).getJSONObject("main").getInt("humidity"));
                weather.setPressure(weatherArray.getJSONObject(i).getJSONObject("main").getDouble("pressure"));

                realm.copyToRealmOrUpdate(weather);
            }
            realm.commitTransaction();
            Log.v(GlobalVar.MY_LOG, "update_ok");

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
