package com.example.alex.psunshine.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alex.psunshine.R;
import com.example.alex.psunshine.activities.SettingActivity;
import com.example.alex.psunshine.database.Contract;
import com.example.alex.psunshine.utilities.Utility;

/**
 * Created by Alex on 06.11.2016.
 */


public class FragmentDetail extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    String hashTag = "#SunshineAPP";
    String StringForecast;


    private static final int DETAIL_LOADER = 0;

    private static final String[] FORECAST_COLUMNS = {
            Contract.WeatherEntry.TABLE_NAME + "." + Contract.WeatherEntry._ID,
            Contract.WeatherEntry.COLUMN_DATE,
            Contract.WeatherEntry.COLUMN_SHORT_DESC,
            Contract.WeatherEntry.COLUMN_MAX_TEMP,
            Contract.WeatherEntry.COLUMN_MIN_TEMP,
    };

    // these constants correspond to the projection defined above, and must change if the
    // projection changes
    private static final int COL_WEATHER_ID = 0;
    private static final int COL_WEATHER_DATE = 1;
    private static final int COL_WEATHER_DESC = 2;
    private static final int COL_WEATHER_MAX_TEMP = 3;
    private static final int COL_WEATHER_MIN_TEMP = 4;

    public FragmentDetail() {
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("log", "Fragment Detail onCreateView");

        return inflater.inflate(R.layout.fragment_detail, container, false);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, savedInstanceState, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail_menu, menu);
        MenuItem itemShare = menu.findItem(R.id.share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(itemShare);
        if (shareActionProvider != null) {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);

            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, StringForecast + "  " + hashTag);
            shareActionProvider.setShareIntent(shareIntent);
        } else {
            Log.d("log", "null");
        }

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Intent intent = getActivity().getIntent();
        if (intent == null) {
            return null;
        }

        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        return new CursorLoader(
                getActivity(),
                intent.getData(),
                FORECAST_COLUMNS,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.v("log", "In onLoadFinished");
        if (!data.moveToFirst()) {
            return;
        }

        String dateString = Utility.formatDate(
                data.getLong(COL_WEATHER_DATE));

        String weatherDescription =
                data.getString(COL_WEATHER_DESC);

        boolean isMetric = Utility.isMetric(getActivity());

        String high = Utility.formatTemperature(
                data.getDouble(COL_WEATHER_MAX_TEMP), isMetric);

        String low = Utility.formatTemperature(
                data.getDouble(COL_WEATHER_MIN_TEMP), isMetric);

        StringForecast = String.format("%s - %s - %s/%s", dateString, weatherDescription, high, low);
        TextView textViewDetail = (TextView) getView().findViewById(R.id.detail_text_id);
        textViewDetail.setText(StringForecast);


    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}

