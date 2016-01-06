package com.isolomonik.toolbaraction.fragments;


import android.content.Context;
import android.os.Bundle;
;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.isolomonik.toolbaraction.utils.CallBackInterface;
import com.isolomonik.toolbaraction.R;
import com.isolomonik.toolbaraction.utils.WeatherAdapter;
import com.isolomonik.toolbaraction.models.WeatherData;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


public class ListViewFragment extends Fragment {

    private CallBackInterface callBackInterface;
    Realm realm;

    private RecyclerView recyclerView;
    private WeatherAdapter adapter;

    public ListViewFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callBackInterface = (CallBackInterface) context;
        } catch (ClassCastException exception) {
            throw new ClassCastException(context.toString());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_list, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView = (RecyclerView) view.findViewById(R.id.listView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<WeatherData> result = realm.where(WeatherData.class).findAll();
        ArrayList<WeatherData> weatherList = new ArrayList<>();
        weatherList.addAll(result.subList(0, result.size()));
        realm.commitTransaction();
        adapter = new WeatherAdapter(getView().getContext(), weatherList);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onStop() {
        realm.close();
        super.onStop();

    }
}
