package com.example.alex.psunshine.activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.alex.psunshine.R;
import com.example.alex.psunshine.fragments.FragmentForecast;
import com.example.alex.psunshine.getForecast.FetchForecast;

public class MainActivity extends AppCompatActivity {
    ShareActionProvider actionShare;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentForecast forecast = new FragmentForecast();
        getSupportFragmentManager().beginTransaction().add(R.id.main_activity_frame, forecast).commit();
        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);
        // getRefreshWeather();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_geo:
                getGeoIntent();
                break;
            case R.id.menu_refresh:
                Log.d("log", "Refresh");
                getRefreshWeather();
                break;
            case R.id.menu_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void getRefreshWeather() {
        FetchForecast getWeather = new FetchForecast(this, null);

        String location;
        String measure;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        location = preferences.getString(getString(R.string.pref_location_key), getString(R.string.pref_def_location));
        measure = preferences.getString(getString(R.string.pref_measure_key), getString(R.string.pref_def_measure));
        getWeather.execute(location, measure);
    }

    public void getGeoIntent() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String location = preferences.getString(getString(R.string.pref_location_key), getString(R.string.pref_def_location));
        Uri geoLocation = Uri.parse("geo:0,0?").
                buildUpon().
                appendQueryParameter("q", location).
                build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d("log", "Cant get Location");
        }

    }
}
