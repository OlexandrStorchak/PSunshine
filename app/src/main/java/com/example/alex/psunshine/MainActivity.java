package com.example.alex.psunshine;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import static android.support.v7.widget.ActionMenuView.*;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentForecast forecast = new FragmentForecast();
        getSupportFragmentManager().beginTransaction().add(R.id.main_activity_frame, forecast).commit();
        PreferenceManager.setDefaultValues(this,R.xml.pref_general,false);
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

        }
        return super.onOptionsItemSelected(item);
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
            Log.d("log","Cant get Location");
        }

    }
}
