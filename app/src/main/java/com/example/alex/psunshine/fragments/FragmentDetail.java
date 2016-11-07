package com.example.alex.psunshine.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import static com.example.alex.psunshine.activities.DetailActivity.detailString;

/**
 * Created by Alex on 06.11.2016.
 */


public class FragmentDetail extends Fragment {
    TextView textViewDetail;
    String hashTag = "#SunshineAPP";

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
    public void onStart() {
        super.onStart();
        setDetail(getView());

    }

    private void setDetail(View view) {
        textViewDetail = (TextView) view.findViewById(R.id.detail_text_id);
        textViewDetail.setText(detailString);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail_menu, menu);
        MenuItem itemShare = menu.findItem(R.id.share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(itemShare);
        if (shareActionProvider != null) {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);

            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, detailString +" "+ hashTag);
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
}
