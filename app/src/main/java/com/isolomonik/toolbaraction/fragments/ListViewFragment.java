package com.isolomonik.toolbaraction.fragments;


import android.content.Context;
import android.os.Bundle;
;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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

   ListView listView;
    private WeatherAdapter adapter;

    public ListViewFragment() {
           }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callBackInterface = (CallBackInterface) context;
                    } catch (ClassCastException exception) {
            throw new ClassCastException(context.toString() + " Must implement CallbackInterface");
        }
         realm = Realm.getInstance(context);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        realm = Realm.getInstance(getContext());

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        listView = (ListView) view.findViewById(R.id.listView);

        realm.beginTransaction();
        RealmResults<WeatherData> result = realm.where(WeatherData.class).findAll();
        ArrayList<WeatherData> weatherList =new ArrayList<>();
        weatherList.addAll(result.subList(0, result.size()));
        realm.commitTransaction();
        adapter = new WeatherAdapter(getView().getContext(),weatherList);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                callBackInterface.updateContent(position);
            }

    });
    }
}
