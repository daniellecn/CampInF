package com.campin.DB;

import android.database.sqlite.SQLiteDatabase;

import com.campin.Utils.PlannedTrip;

import java.util.List;

/**
 * Created by noam on 03/06/2017.
 */

class PlannedTripSql {

    public static void create(SQLiteDatabase db) {
    }
    
    public static void addTrip(SQLiteDatabase writableDatabase, PlannedTrip trip) {
    }

    public static void getMaxIdPlannedTrip(SQLiteDatabase writableDatabase, PlannedTrip trip) {
    }

    public static void updateDessert(SQLiteDatabase writableDatabase, PlannedTrip trip) {
    }

    public static PlannedTrip getDessertById(SQLiteDatabase readableDatabase, String s) {
        return null;

    }

    public static List<PlannedTrip> getAllPlannedTrip(SQLiteDatabase readableDatabase) {
        return null;
    }

    public static List<PlannedTrip> getDessertsBySearch(SQLiteDatabase readableDatabase, String query) {
        return null;
    }

    public static void dropTable(SQLiteDatabase db) {
    }

    public static double getLastUpdateDate(SQLiteDatabase readbleDB) {
        return 1234;
    }

    public static List<PlannedTrip> getAllDesserts(SQLiteDatabase readbleDB) {
        return  null;
    }
}
