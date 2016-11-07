package com.example.alex.psunshine.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.alex.psunshine.R;
import com.example.alex.psunshine.activities.DetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentForecast extends Fragment {

    public static List<String> forecastStr = new ArrayList<>();
    public static ArrayAdapter<String> adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Log.d("log", "FragmentForecast OnCreateView");
        return inflater.inflate(R.layout.fragment_forecast, container, false);


    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("log", "FragmentForecast onStart");
        showForecast(getView());


    }

    public void showForecast(View v) {

        ListView listView = (ListView) v.findViewById(R.id.forecast_list_view);

        adapter = new ArrayAdapter<>(getContext(), R.layout.forecast_row, new ArrayList<String>(forecastStr));
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("log", "Clicked " + i);
                // Toast.makeText(getContext(),adapter.getItem(i), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), DetailActivity.class).putExtra("details", adapter.getItem(i)));
            }
        });


    }


}
