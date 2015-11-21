package com.isolomonik.toolbaraction;


import android.content.Context;
import android.os.Bundle;
;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;


public class ListViewFragment extends Fragment {

    private CallBackInterface callBackInterface;

   ListView listView;

    static ArrayList<HashMap<String, String>> WeatherData;

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
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Listen to clicks ...
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                callBackInterface.updateContent(position);
            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Starting AsynkTask to download and parse data
        FetchWeater fetchWeater = new FetchWeater();
        fetchWeater.execute();
        listView = (ListView) view.findViewById(R.id.listView);
    }
}
