package com.example.alex.psunshine;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class DetailActivity extends AppCompatActivity {
    public static String detailString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        FragmentDetail fragmentDetail =  new FragmentDetail();
        getFragmentManager().beginTransaction().add(R.id.detail_activity_frame,fragmentDetail).commit();
        Intent intent = getIntent();
        detailString = intent.getStringExtra("details");
        Log.d("log","Activity Detail onCreate");


    }



}
