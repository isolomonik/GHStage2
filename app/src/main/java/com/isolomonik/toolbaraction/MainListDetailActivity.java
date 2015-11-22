package com.isolomonik.toolbaraction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainListDetailActivity extends AppCompatActivity implements CallBackInterface {
    public Fragment listFragment;
    public DetailFragment detailFragment;
    FragmentManager fm = getSupportFragmentManager();
    private ProgressDialog progressDialog;
    public static List<HourlyWeather> weather = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainlistdetail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            // Starting AsynkTask to download and parse data
            LoadWeather loadWeather = new LoadWeather();
            loadWeather.execute();
        }


    }

    void initFragments() {
        listFragment = new ListViewFragment();
        detailFragment = new DetailFragment();
        fm.beginTransaction().add(R.id.listCont, listFragment).commit();
        if (findViewById(R.id.detailCont) != null) {
            if (detailFragment.isInLayout()) {
                fm.beginTransaction().replace(R.id.detailCont, detailFragment).commit();
            } else {
                fm.beginTransaction().add(R.id.detailCont, detailFragment).commit();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


    @Override
    public void updateContent(int position) {

        detailFragment.setItemContent(position);
        if (detailFragment.isVisible()) {
            fm.beginTransaction().replace(R.id.detailCont, detailFragment).commit();
        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        }
    }


    public class LoadWeather extends AsyncTask<Void, Void, List<HourlyWeather>> {


        String dummyAppid = "0ebf995a77d995a5b5dabb0bff29b368";
        String queryWeather = "http://api.openweathermap.org/data/2.5/forecast/hourly?q=Cherkasy,ua&units=metric";
        String queryDummyKey = "&appid=" + dummyAppid;
        String json = "";

        @Override
        protected List<HourlyWeather> doInBackground(Void... params) {

            String query = null;
            try {
                query = queryWeather
                        //+ URLEncoder.encode(cityName, "UTF-8")
                        + queryDummyKey;

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

                JsonObject jsonWeather = new JsonParser().parse(json).getAsJsonObject();

                Gson gson = new Gson();
                for (JsonElement element : jsonWeather.getAsJsonArray("list")) {
                    weather.add(gson.fromJson(element, HourlyWeather.class));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return weather;
        }

        @Override
        protected void onPostExecute(List<HourlyWeather> hourlyWeathers) {
            progressDialog.dismiss();
            super.onPostExecute(hourlyWeathers);
            initFragments();

        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainListDetailActivity.this);
            progressDialog.setMessage("Wait please");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
            super.onPreExecute();
        }
    }

}
