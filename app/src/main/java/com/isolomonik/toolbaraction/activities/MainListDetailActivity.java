package com.isolomonik.toolbaraction.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.isolomonik.toolbaraction.services.LoadDataService;
import com.isolomonik.toolbaraction.services.NotificationService;
import com.isolomonik.toolbaraction.utils.CallBackInterface;
import com.isolomonik.toolbaraction.fragments.DetailFragment;
import com.isolomonik.toolbaraction.fragments.ListViewFragment;
import com.isolomonik.toolbaraction.R;
import com.isolomonik.toolbaraction.models.WeatherData;
import com.isolomonik.toolbaraction.utils.GlobalVar;


import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;

public class MainListDetailActivity extends AppCompatActivity  implements CallBackInterface {
    public Fragment listFragment;
    public DetailFragment detailFragment;
    private int position = 0;
    private FragmentManager fm = getSupportFragmentManager();
    private ProgressDialog progressDialog;
    LoadWeather loadWeather;
    public  Realm realm;

    private ArrayList<WeatherData> weatherList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainlistdetail);

        try {
            realm = Realm.getDefaultInstance();
        } catch (RealmMigrationNeededException ex) {
            ex.printStackTrace();
                    }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.ic_10d);

        getSupportActionBar().setTitle(R.string.city);

        if (savedInstanceState == null) {
            if (GlobalVar.isNetworkAvailable(this)) {
                if (loadWeather == null) {
            loadWeather = new LoadWeather();
            loadWeather.execute();}

        } else {
                getweatherList();
                Toast.makeText(this, R.string.no_connection, Toast.LENGTH_SHORT).show();
            }
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("position", position);
        super.onSaveInstanceState(savedInstanceState);
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
            case R.id.stop_notif:
                stopService(new Intent(MainListDetailActivity.this, NotificationService.class));
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void updateContent(int position) {

        if (detailFragment !=null&& detailFragment.isVisible()) {
            detailFragment.setItemContent(position);
            detailFragment.updateDetail();
            fm.beginTransaction().replace(R.id.detailCont, detailFragment).commit();
        } else {
            if (findViewById(R.id.detailCont) != null){
                detailFragment = new DetailFragment();
                detailFragment.setItemContent(position);
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

        if (result.size()>0) {
            weatherList.add(result.get(result.size() - 1));
        }
        realm.commitTransaction();
        return weatherList;
    }


    public class LoadWeather extends AsyncTask<Void, Void, ArrayList<WeatherData>>
            {

        @Override
        protected ArrayList<WeatherData> doInBackground(Void... params) {
            startService(new Intent(MainListDetailActivity.this, LoadDataService.class));
            realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            RealmResults<WeatherData> result = realm.where(WeatherData.class).findAll();
            weatherList.addAll(result.subList(0, result.size()));
            realm.commitTransaction();
            return weatherList;

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
            progressDialog.setMessage(getResources().getString(R.string.waiting));
            progressDialog.setIndeterminate(true);
            progressDialog.show();
            super.onPreExecute();
        }
    }


}
