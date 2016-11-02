package com.example.alex.psunshine;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentForecast extends Fragment {


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


        List<String> forecastStr = new ArrayList<>();
        forecastStr.add("Mon 6/23‚- Sunny - 31/17");
        forecastStr.add("Tue 6/24 - Foggy - 21/8");
        forecastStr.add("Wed 6/25 - Cloudy - 22/17");
        forecastStr.add("Thurs 6/26 - Rainy - 18/11");
        forecastStr.add("Fri 6/27 - Foggy - 21/10");
        forecastStr.add("Sat 6/28 - Sunny - 23/18");
        forecastStr.add("Sun 6/29 - Sunny - 20/7");

        ListAdapter adapter = new ArrayAdapter<>(getContext(),
                R.layout.forecast_row, forecastStr);
        listView.setAdapter(adapter);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_forecast, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                Log.d("log", "Refresh");
                FetchForecast task = new FetchForecast();
                task.execute();
        }
        return super.onOptionsItemSelected(item);
    }
}
