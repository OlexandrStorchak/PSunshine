package com.example.alex.psunshine.activities;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.alex.psunshine.R;
import com.example.alex.psunshine.fragments.FragmentForecast;
import com.example.alex.psunshine.getForecast.FetchForecast;
import com.example.alex.psunshine.utilities.Utility;

public class MainActivity extends AppCompatActivity {
    private final String FORECASTFRAGMENT_TAG = "FFTAG";

    private String mLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLocation = Utility.getPreferredLocation(this);
        FragmentForecast forecast = new FragmentForecast();
        getSupportFragmentManager().beginTransaction().add(R.id.main_activity_frame, forecast, FORECASTFRAGMENT_TAG).commit();
        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        String location = Utility.getPreferredLocation(this);
        // update the location in our second pane using the fragment manager
        if (location != null && !location.equals(mLocation)) {
            FragmentForecast ff = (FragmentForecast) getSupportFragmentManager().findFragmentByTag(FORECASTFRAGMENT_TAG);
            if (null != ff) {
                ff.onLocationChanged();
            }
            mLocation = location;
        }
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
        FetchForecast getWeather = new FetchForecast(this);

        String location = Utility.getPreferredLocation(this);

        getWeather.execute(location);


    }

    public void getGeoIntent() {
        String location = Utility.getPreferredLocation(this);
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
