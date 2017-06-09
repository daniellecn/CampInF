package com.campin.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by noam on 26/05/2017.
 */

public class DBHandler  extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "campIn";

    // table names
    private static final String TABLE_USERS = "USERS";
    private static final String TABLE_TRIPS = "TRIPS";
    private static final String PLANNED_TRIPS = "PLANNED_TRIPS";


    // USER Table Columns names
    private static final String USER_ID = "USER_ID";
    private static final String USER_NAME = "USER_NAME";
    private static final String USER_MAIL = "USER_MAIL";

    // TRIPS Table columns
    private static final String TRIP_ID = "TRIP_ID";
    private static final String TRIP_PLACE = "TRIP_PLACE";
    private static final String TRIP_RATING = "TRIP_RATING";

    // TRIPS_PLANNED Table columns
    private static final String PLANNED_TRIP_ID = "TRIP_ID";
    private static final String MANAGER_ID = "IS_MANAGER";
    private static final String RATE = "RATE";
    private static final String IS_COMPLETED = "IS_COMPLETED";

    // TRIPS_FOR_USERS
    private static final String PARTICIPATE_ID = "PARTICIPATE_ID";
    //TRIPS ID

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
        + USER_ID + " INTEGER PRIMARY KEY," + USER_NAME + " TEXT,"
        + USER_MAIL + " TEXT" + ")";

        String CREATE_TRIPS_TABLE = "CREATE TABLE " + TABLE_TRIPS + "("
                + USER_ID + " INTEGER PRIMARY KEY," + USER_NAME + " TEXT,"
                + USER_MAIL + " TEXT" + ")";


        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
