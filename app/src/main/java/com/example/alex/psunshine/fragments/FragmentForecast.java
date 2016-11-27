package com.example.alex.psunshine.fragments;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.alex.psunshine.R;
import com.example.alex.psunshine.activities.DetailActivity;
import com.example.alex.psunshine.adapters.ForecastAdapter;
import com.example.alex.psunshine.database.Contract;
import com.example.alex.psunshine.utilities.Utility;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentForecast extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private ForecastAdapter adapter;

    private static final int FORECAST_LOADER = 0;

    static final int COL_WEATHER_ID = 0;

    public static final int COL_WEATHER_DATE = 2;

    public static final int COL_WEATHER_DESC = 3;

    public static final int COL_WEATHER_MAX_TEMP = 5;

    public static final int COL_WEATHER_MIN_TEMP = 6;

    static final int COL_LOCATION_SETTING = 8;

    static final int COL_WEATHER_CONDITION_ID = 6;

    static final int COL_COORD_LAT = 7;

    static final int COL_COORD_LONG = 8;

    private static final String[] FORECAST_COLUMNS = {

            Contract.WeatherEntry.TABLE_NAME + "." + Contract.WeatherEntry._ID,
            Contract.WeatherEntry.COLUMN_DATE,
            Contract.WeatherEntry.COLUMN_SHORT_DESC,
            Contract.WeatherEntry.COLUMN_MAX_TEMP,
            Contract.WeatherEntry.COLUMN_MIN_TEMP,
            Contract.LocationEntry.COLUMN_LOCATION_SETTING,
            Contract.WeatherEntry.COLUMN_WEATHER_ID,
            Contract.LocationEntry.COLUMN_LATITUDE,
            Contract.LocationEntry.COLUMN_LONGTITUDE
    };

    Cursor cur = null;

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

        String locationSetting = Utility.getPreferredLocation(getActivity());
        Uri weatherForLocationUri = Contract.WeatherEntry.buildWeatherLocationWithStartDate(
                locationSetting, System.currentTimeMillis());
        String sortOrder = Contract.WeatherEntry.COLUMN_DATE + " ASC";
        cur = getActivity().getContentResolver().query(weatherForLocationUri,
                null, null, null, sortOrder);

        adapter = new ForecastAdapter(getActivity(), cur, 0);

        ListView listView = (ListView) getView().findViewById(R.id.forecast_list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView adapterView, View view, int position, long l) {
                // CursorAdapter returns a cursor at the correct position for getItem(), or null
                // if it cannot seek to that position.
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (cursor != null) {
                    String locationSetting = Utility.getPreferredLocation(getActivity());
                    Intent intent = new Intent(getActivity(), DetailActivity.class)
                            .setData(Contract.WeatherEntry.buildWeatherLocationWithDate(
                                    locationSetting, cursor.getLong(COL_WEATHER_DATE)
                            ));
                    startActivity(intent);
                }
            }
        });


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(FORECAST_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String locationSetting = Utility.getPreferredLocation(getActivity());
        String sortOrder = Contract.WeatherEntry.COLUMN_DATE + " ASC";
        Uri weatherForLocationUri = Contract.WeatherEntry.buildWeatherLocationWithStartDate(
                locationSetting, System.currentTimeMillis());

        return new CursorLoader(getActivity(),
                weatherForLocationUri,
                FORECAST_COLUMNS,
                null,
                null,
                sortOrder);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(cur);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    public void onLocationChanged() {
        //getRefreshWeather();
        // getLoaderManager().restartLoader(FORECAST_LOADER, null, this);
    }
}
