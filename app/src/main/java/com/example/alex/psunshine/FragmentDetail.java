package com.example.alex.psunshine;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static com.example.alex.psunshine.DetailActivity.detailString;

/**
 * Created by Alex on 06.11.2016.
 */


public class FragmentDetail extends Fragment {
    TextView textViewDetail;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("log","Fragment Detail onCreateView");
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_detail,container,false);


    }

    @Override
    public void onStart() {
        super.onStart();
        setDetail(getView());
    }

    private void setDetail(View view){
        textViewDetail = (TextView)view.findViewById(R.id.detail_text_id);
        textViewDetail.setText(detailString);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case  R.id.menu_setting:
                startActivity(new Intent(getActivity(),SettingActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
