package com.isolomonik.toolbaraction;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainListDetailActivity extends AppCompatActivity implements CallBackInterface {
    public Fragment listFragment;
    public DetailFragment detailFragment;
    FragmentManager fm;
    static int  LIST_POSITION = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainlistdetail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//LIST_POSITION=0;
        listFragment = new ListViewFragment();
        detailFragment = new DetailFragment();


        if (findViewById(R.id.detailCont) != null) {
            if (detailFragment.isInLayout()) {
                getSupportFragmentManager().beginTransaction().replace(R.id.detailCont, detailFragment).commit();
            } else {
                getSupportFragmentManager().beginTransaction().add(R.id.detailCont, detailFragment).commit();
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
        if (detailFragment.isInLayout()) {
           fm.beginTransaction().replace(R.id.detailCont, detailFragment).commit();
        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        }
    }
}
