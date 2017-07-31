package com.campin.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.campin.Utils.Trip;
import com.campin.Utils.TripComments;
import com.campin.Utils.TripLevel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Danielle Cohen on 28/07/2017.
 */

public class TripLevelSql {
    // Trip levels Table
    private static final String TRIP_LEVEL_TABLE = "TRIP_LEVEL";
    private static final String CODE = "CODE";
    private static final String DESCRIPTION = "DESCRIPTION";

    public static void createTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TRIP_LEVEL_TABLE + " (" +
                CODE + " NUM PRIMARY KEY," +
                DESCRIPTION + " TEXT );");
    }

    public static void dropTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE " + TRIP_LEVEL_TABLE);
    }

    public static void addTripLevel(SQLiteDatabase db, TripLevel tripLevel) {
        ContentValues values = new ContentValues();
        long rowId;

        // Set Trip levels table values
        values.put(CODE, tripLevel.getCode());
        values.put(DESCRIPTION, tripLevel.getDescription());

        // Add to local db
        rowId = db.insertWithOnConflict(TRIP_LEVEL_TABLE, CODE, values, SQLiteDatabase.CONFLICT_REPLACE);
        if (rowId <= 0) {
            Log.e("SQLite", "fail to insert into trips");
        }
    }

    public static TripLevel getTripLevelByCode(SQLiteDatabase db, int id) {
        // Set the selection parameters
        String[] selectArg = {String.valueOf(id)};
        TripLevel tripLevel = null;

        // TRIP
        Cursor cursor = db.query(TRIP_LEVEL_TABLE, null, CODE + " = ?", selectArg, null, null, null);

        if (cursor.moveToFirst() == true) {
            tripLevel = new TripLevel(
                    cursor.getInt(cursor.getColumnIndex(CODE)),
                    cursor.getString(cursor.getColumnIndex(DESCRIPTION)));
        }
        return tripLevel;
    }

    public static double getLastUpdateDate(SQLiteDatabase db) {
        return LastUpdateSql.getLastUpdate(db, TRIP_LEVEL_TABLE);
    }

    public static void setLastUpdateDate(SQLiteDatabase db, double date) {
        LastUpdateSql.setLastUpdate(db, TRIP_LEVEL_TABLE, date);
    }

    public static List<TripLevel> getAllTripLevels(SQLiteDatabase db) {
        List<TripLevel> tripLevelsList = new LinkedList<>();
        String[] arg = new String[1];
        TripLevel tripLevel;

        Cursor tripLevelCursor = db.query(TRIP_LEVEL_TABLE, null, null, null, null, null, null);

        // If there is trips
        if (tripLevelCursor.moveToFirst()){
            // Defined indexes
            int codeIndex = tripLevelCursor.getColumnIndex(CODE);
            int descriptionIndex = tripLevelCursor.getColumnIndex(DESCRIPTION);

            // Select argument
            arg[0] = tripLevelCursor.getString(codeIndex);

            do {
                // Create trip level object
                tripLevel = new TripLevel(
                        tripLevelCursor.getInt(codeIndex),
                        tripLevelCursor.getString(descriptionIndex)
                );
                tripLevelsList.add(tripLevel);
            }while (tripLevelCursor.moveToNext());
        }

        return tripLevelsList;
    }

    public static void updateTripLevel(SQLiteDatabase db, TripLevel level) {
        ContentValues values = new ContentValues();

        // Set values
        values.put(CODE, level.getCode());
        values.put(DESCRIPTION, level.getDescription());

        String whereClause = CODE + " = ?";
        String[] whereArg = new String[]{String.valueOf(level.getCode())};

        // Add to local db
        long rowId = db.updateWithOnConflict(TRIP_LEVEL_TABLE, values, whereClause, whereArg, SQLiteDatabase.CONFLICT_REPLACE);
        if (rowId <= 0) {
            Log.e("SQLite", "fail to insert into student");
        }
    }
}
