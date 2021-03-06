package com.isolomonik.toolbaraction.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.isolomonik.toolbaraction.fragments.DetailFragment;
import com.isolomonik.toolbaraction.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.city);

        DetailFragment fragment=new DetailFragment();

        int position = getIntent().getExtras().getInt("position");
        fragment.setItemContent(position);
        if (savedInstanceState == null) {
           getSupportFragmentManager().beginTransaction().add(R.id.detailCont, fragment).commit();
        } else {
           // fragment.updateDetail();
            getSupportFragmentManager().beginTransaction().replace(R.id.detailCont, fragment).commit();

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
