package com.example.alex.psunshine.database;

/**
 * Created by Alex on 08.11.2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Manages a local database for weather data.
 */
public class WeatherDataBase extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 2;

    public static final String DATABASE_NAME = "weather.db";

    public WeatherDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + Contract.LocationEntry.TABLE_NAME + " (" +
                Contract.LocationEntry._ID + " INTEGER PRIMARY KEY," +
                Contract.LocationEntry.COLUMN_LOCATION_SETTING + " TEXT UNIQUE NOT NULL, " +
                Contract.LocationEntry.COLUMN_CITY + " TEXT NOT NULL, " +
                Contract.LocationEntry.COLUMN_LATITUDE + " REAL NOT NULL, " +
                Contract.LocationEntry.COLUMN_LONGTITUDE + " REAL NOT NULL " +
                " );";

        final String SQL_CREATE_WEATHER_TABLE = "CREATE TABLE " + Contract.WeatherEntry.TABLE_NAME + " (  " +
                Contract.WeatherEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,  " +
                Contract.WeatherEntry.COLUMN_LOC_KEY + " INTEGER NOT NULL, " +
                Contract.WeatherEntry.COLUMN_DATE + " INTEGER NOT NULL, " +
                Contract.WeatherEntry.COLUMN_SHORT_DESC + " TEXT NOT NULL, " +
                Contract.WeatherEntry.COLUMN_WEATHER_ID + " INTEGER NOT NULL," +
                Contract.WeatherEntry.COLUMN_MIN_TEMP + " REAL NOT NULL, " +
                Contract.WeatherEntry.COLUMN_MAX_TEMP + " REAL NOT NULL, " +
                Contract.WeatherEntry.COLUMN_HUMIDITY + " REAL NOT NULL, " +
                Contract.WeatherEntry.COLUMN_PRESSURE + " REAL NOT NULL, " +
                Contract.WeatherEntry.COLUMN_WIND_SPEED + " REAL NOT NULL, " +
                Contract.WeatherEntry.COLUMN_DEGREES + " REAL NOT NULL, " +
                " FOREIGN KEY (  " + Contract.WeatherEntry.COLUMN_LOC_KEY + "  ) REFERENCES " +
                Contract.LocationEntry.TABLE_NAME + " (  " + Contract.LocationEntry._ID + "  ), " +
                " UNIQUE (  " + Contract.WeatherEntry.COLUMN_DATE + ", " +
                Contract.WeatherEntry.COLUMN_LOC_KEY + "  ) ON CONFLICT REPLACE);  ";

        sqLiteDatabase.execSQL(SQL_CREATE_LOCATION_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_WEATHER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Contract.LocationEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Contract.WeatherEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}