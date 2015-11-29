package com.isolomonik.toolbaraction;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;

public class MainListDetailActivity extends AppCompatActivity  implements CallBackInterface {
    public Fragment listFragment;
    public DetailFragment detailFragment;
    private FragmentManager fm = getSupportFragmentManager();
    private ProgressDialog progressDialog;
    public  Realm realm;
    public static RealmConfiguration realmConfiguration ;

    //List<HourlyWeather> weather = new ArrayList<>();
    private ArrayList<WeatherData> weatherList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainlistdetail);

        try {
            realmConfiguration = new RealmConfiguration.Builder(MainListDetailActivity.this).build();
            realm.setDefaultConfiguration(realmConfiguration);
            realm = Realm.getInstance(realmConfiguration);
        } catch (RealmMigrationNeededException ex) {
            ex.printStackTrace();
                    }



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.city);

        if (savedInstanceState == null) {
            if (isNetworkAvailable()){

            // Starting AsynkTask to download and parse data
            LoadWeather loadWeather = new LoadWeather();
            loadWeather.execute();
        } else {
                getweatherList();
                Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
            }
        }


    }
private boolean isNetworkAvailable(){
    ConnectivityManager cm= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo =cm.getActiveNetworkInfo();
    return activeNetworkInfo!=null;
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

        if (detailFragment!=null&& detailFragment.isVisible()) {
            detailFragment.setItemContent(position);
            detailFragment.updateDetail();

            realm = Realm.getInstance(realmConfiguration);
            fm.beginTransaction().replace(R.id.detailCont, detailFragment).commit();
        } else {
            if (findViewById(R.id.detailCont) != null){
                detailFragment = new DetailFragment();
                detailFragment.setItemContent(position);
                detailFragment.updateDetail();
                fm.beginTransaction().add(R.id.detailCont, detailFragment).commit();
            }else{
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);}
        }
    }


    @Override
    public ArrayList<WeatherData> getweatherList() {

        if (realm.allObjects(WeatherData.class).size() != 0) {
            weatherList.addAll(realm.allObjects(WeatherData.class));
            initFragments();
            } else {
            }

        realm.beginTransaction();
        RealmResults<WeatherData> result = realm.where(WeatherData.class).findAll();
        ArrayList<WeatherData> weatherList =new ArrayList<>();
        weatherList.add(result.get(result.size()-1));
        realm.commitTransaction();
        return weatherList;
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        realm.close();
//    }


    public class LoadWeather extends AsyncTask<Void, Void, ArrayList<WeatherData>>
            {

        @Override
        protected ArrayList<WeatherData> doInBackground(Void... params) {
      //  protected List<HourlyWeather> doInBackground() {

          return fetchData();
        }

        @Override
        protected void onPostExecute(ArrayList<WeatherData> wList) {
            progressDialog.dismiss();
            super.onPostExecute(wList);
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

 ArrayList<WeatherData> fetchData(){
     String dummyAppid = "0ebf995a77d995a5b5dabb0bff29b368";
     String queryWeather = "http://api.openweathermap.org/data/2.5/forecast/hourly?q=Cherkasy,ua&units=metric";
     String queryDummyKey = "&appid=" + dummyAppid;
     String json = "";
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

         //    JsonObject gsonWeather = new JsonParser().parse(json).getAsJsonObject();

         JSONObject jsonWeather = new JSONObject(json);

         JSONArray weatherArray = jsonWeather.getJSONArray("list");


//         Realm.deleteRealm(realmConfiguration);
         realm = Realm.getInstance(realmConfiguration);
         realm.beginTransaction();

         //  realm.createAllFromJson(WeatherData.class, weatherArray);
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
            // weatherList.add(weather);
             realm.copyToRealmOrUpdate(weather);
         }


         realm.commitTransaction();
         realm.beginTransaction();
         RealmResults <WeatherData> result = realm.where(WeatherData.class).findAll();
         weatherList.addAll(result.subList(0, result.size()));
         realm.commitTransaction();



     } catch (JSONException e) {
         e.printStackTrace();
     } catch (IOException e) {
         e.printStackTrace();
     }
// finally {
//         realm.close();
//     }
     return weatherList;
 }
}
