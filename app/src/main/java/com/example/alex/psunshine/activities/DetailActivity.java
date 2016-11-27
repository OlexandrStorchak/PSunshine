package com.example.alex.psunshine.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.alex.psunshine.R;
import com.example.alex.psunshine.fragments.FragmentDetail;

public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportFragmentManager().beginTransaction().add(R.id.detail_activity_frame, new FragmentDetail()).commit();


    }
}





