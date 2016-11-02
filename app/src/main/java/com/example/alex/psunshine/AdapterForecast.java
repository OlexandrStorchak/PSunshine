package com.example.alex.psunshine;

import android.content.Context;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by Alex on 02.11.2016.
 */

public class AdapterForecast extends SimpleAdapter {
    public AdapterForecast(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }

}
