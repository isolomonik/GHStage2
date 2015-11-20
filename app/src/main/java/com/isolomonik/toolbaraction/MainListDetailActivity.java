package com.isolomonik.toolbaraction;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

public class MainListDetailActivity extends AppCompatActivity {
public ListView listView;
public Fragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainlistdetail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView =(ListView) findViewById(R.id.listView);
        detailFragment= new DetailFragment();
        if (findViewById(R.id.detailCont) == null ) {
//            Intent intent = new Intent(this, Lesson6DetailActivity.class);
//            intent.putExtra("position", position);
//            startActivity(intent);

////                 lesson6DetailFragment = lesson6DetailFragment.newInstance(pos);
//                   getSupportFragmentManager().beginTransaction().replace(R.id.cont, lesson6DetailFragment).commit();
        }
        else {
           if (detailFragment.isInLayout()) {
               getSupportFragmentManager().beginTransaction().replace(R.id.detailCont, detailFragment).commit();
           }else {
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
}
