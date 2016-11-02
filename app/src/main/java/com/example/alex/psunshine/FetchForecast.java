package com.example.alex.psunshine;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Alex on 02.11.2016.
 */


public class FetchForecast extends AsyncTask<String, Void, Void> {
    HttpURLConnection httpURLConnection = null;
    BufferedReader bufferedReader = null;
    String jSonString;


    @Override
    protected Void doInBackground(String... strings) {
        try {
            Log.d("log", "In background");

            URL url = new URL("https://www.google.com.ua/");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            inputStream.read();
            Log.d("log", "In background end");


        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d("log", "In background URL Exception");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("log", "In background IO Exception");
        } finally {
            httpURLConnection.disconnect();
            Log.d("log", "In background finaly");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
