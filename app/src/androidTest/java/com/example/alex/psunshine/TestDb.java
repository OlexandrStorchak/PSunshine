package com.example.alex.psunshine;

/**
 * Created by Alex on 08.11.2016.
 */

/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


        import android.content.ContentValues;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.test.AndroidTestCase;

        import com.example.alex.psunshine.database.Contract;
        import com.example.alex.psunshine.database.WeatherDataBase;

        import java.util.HashSet;

public class TestDb extends AndroidTestCase {

    public static final String LOG_TAG = TestDb.class.getSimpleName();

    // Since we want each test to start with a clean slate
    void deleteTheDatabase() {
        getContext().deleteDatabase(WeatherDataBase.DATABASE_NAME);
    }

    /*
        This function gets called before each test is executed to delete the database.  This makes
        sure that we always have a clean test.
     */
    public void setUp() {
        deleteTheDatabase();
    }



    public void testCreateDb() throws Throwable {
        // build a HashSet of all of the table names we wish to look for
        // Note that there will be another table in the DB that stores the
        // Android metadata (db version information)
        final HashSet<String> tableNameHashSet = new HashSet<String>();
        tableNameHashSet.add(Contract.LocationEntry.TABLE_NAME);
        tableNameHashSet.add(Contract.WeatherEntry.TABLE_NAME);

        getContext().deleteDatabase(WeatherDataBase.DATABASE_NAME);
        SQLiteDatabase sqLiteDatabase = new WeatherDataBase(getContext()).getWritableDatabase();
        assertEquals(true, sqLiteDatabase.isOpen());

        // have we created the tables we want?
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that the database has not been created correctly",
                cursor.moveToFirst());

        // verify that the tables have been created
        do {
            tableNameHashSet.remove(cursor.getString(0));
        } while( cursor.moveToNext() );

        // if this fails, it means that your database doesn't contain both the location entry
        // and weather entry tables
        assertTrue("Error: Your database was created without both the location entry and weather entry tables", tableNameHashSet.isEmpty());

        // now, do our tables contain the correct columns?
        cursor = sqLiteDatabase.rawQuery("PRAGMA table_info(" + Contract.LocationEntry.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                cursor.moveToFirst());

        // Build a HashSet of all of the column names we want to look for
        final HashSet<String> locationColumnHashSet = new HashSet<String>();
        locationColumnHashSet.add(Contract.LocationEntry._ID);
        locationColumnHashSet.add(Contract.LocationEntry.COLUMN_CITY);
        locationColumnHashSet.add(Contract.LocationEntry.COLUMN_LATITUDE);
        locationColumnHashSet.add(Contract.LocationEntry.COLUMN_LONGTITUDE);
        locationColumnHashSet.add(Contract.LocationEntry.COLUMN_LOCATION_SETTING);

        int columnNameIndex = cursor.getColumnIndex("name");
        do {
            String columnName = cursor.getString(columnNameIndex);
            locationColumnHashSet.remove(columnName);
        } while(cursor.moveToNext());

        // if this fails, it means that your database doesn't contain all of the required location
        // entry columns
        assertTrue("Error: The database doesn't contain all of the required location entry columns",
                locationColumnHashSet.isEmpty());
        sqLiteDatabase.close();
    }


    public void testLocationTable() {
       insertLocation();


    }


    public void testWeatherTable() {
        long locationRowId = insertLocation();
        assertFalse("Error: Location Not Inserted Correctly", locationRowId == -1L);

        WeatherDataBase weatherDataBase = new WeatherDataBase(getContext());
        SQLiteDatabase sqLiteDatabase = weatherDataBase.getWritableDatabase();

        ContentValues testValues = TestUtilities.createWeatherValues(locationRowId);

        long weatherRowId = sqLiteDatabase.insert(Contract.WeatherEntry.TABLE_NAME, null, testValues);
        assertTrue(weatherRowId != -1);



        Cursor cursor = sqLiteDatabase.query(Contract.WeatherEntry.TABLE_NAME,
                null, // all columns
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );


        assertTrue( "Error: No Records returned from location query", cursor.moveToFirst() );

        TestUtilities.validateCurrentRecord("Error: Location Query Validation Failed", cursor, testValues);

        assertFalse( "Error: More than one record returned from location query",cursor.moveToNext() );

        cursor.close();

        sqLiteDatabase.close();




    }


    /*
        Students: This is a helper method for the testWeatherTable quiz. You can move your
        code from testLocationTable to here so that you can call this code from both
        testWeatherTable and testLocationTable.
     */
    private long insertLocation() {
        WeatherDataBase weatherDataBase = new WeatherDataBase(getContext());
        SQLiteDatabase sqLiteDatabase = weatherDataBase.getWritableDatabase();

        ContentValues testValues = TestUtilities.createNorthPoleLocationValues();
        long locationRowId;
        locationRowId = sqLiteDatabase.insert(Contract.LocationEntry.TABLE_NAME, null, testValues);
        assertTrue(locationRowId != -1);

        Cursor cursor = sqLiteDatabase.query(Contract.LocationEntry.TABLE_NAME,
                null, // all columns
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );
        assertTrue( "Error: No Records returned from location query", cursor.moveToFirst() );

        TestUtilities.validateCurrentRecord("Error: Location Query Validation Failed",cursor, testValues);

        assertFalse( "Error: More than one record returned from location query",cursor.moveToNext() );

        cursor.close();

        sqLiteDatabase.close();

        return locationRowId;
    }
}