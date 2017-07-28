package com.campin.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.campin.Utils.TripType;

import java.util.List;

/**
 * Created by Danielle Cohen on 28/07/2017.
 */

public class TripTypeSql {
    // Trip Types Table
    private static final String TRIP_TYPE_TABLE = "TRIP_TYPE";
    private static final String CODE = "CODE";
    private static final String DESCRIPTION = "DESCRIPTION";

    public static void createTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TRIP_TYPE_TABLE + " (" +
                CODE + " NUM PRIMARY KEY," +
                DESCRIPTION + " TEXT );");
    }

    public static void dropTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE " + TRIP_TYPE_TABLE);
    }

    public static void addTripType(SQLiteDatabase db, TripType tripType) {
        ContentValues values = new ContentValues();
        long rowId;

        // Set Trip Types table values
        values.put(CODE, tripType.getCode());
        values.put(DESCRIPTION, tripType.getDescription());

        // Add to local db
        rowId = db.insertWithOnConflict(TRIP_TYPE_TABLE, CODE, values, SQLiteDatabase.CONFLICT_REPLACE);
        if (rowId <= 0) {
            Log.e("SQLite", "fail to insert into trips");
        }
    }

    public static TripType getTripTypeByCode(SQLiteDatabase db, int id) {
        // Set the selection parameters
        String[] selectArg = {String.valueOf(id)};
        TripType tripType = null;

        // TRIP
        Cursor cursor = db.query(TRIP_TYPE_TABLE, null, CODE + " = ?", selectArg, null, null, null);

        if (cursor.moveToFirst() == true) {
            tripType = new TripType(
                    cursor.getInt(cursor.getColumnIndex(CODE)),
                    cursor.getString(cursor.getColumnIndex(DESCRIPTION)));
        }
        return tripType;
    }

    public static double getLastUpdateDate(SQLiteDatabase db) {
        return LastUpdateSql.getLastUpdate(db, TRIP_TYPE_TABLE);
    }

    public static void setLastUpdateDate(SQLiteDatabase db, double date) {
        LastUpdateSql.setLastUpdate(db, TRIP_TYPE_TABLE, date);
    }

    public static List<TripType> getAllTripTypes(SQLiteDatabase db) {
        List<TripType> tripTypesList = null;
        String[] arg = new String[1];
        TripType tripType;

        Cursor tripTypeCursor = db.query(TRIP_TYPE_TABLE, null, null, null, null, null, null);

        // If there is trips
        if (tripTypeCursor.moveToFirst()){
            // Defined indexes
            int codeIndex = tripTypeCursor.getColumnIndex(CODE);
            int descriptionIndex = tripTypeCursor.getColumnIndex(DESCRIPTION);

            // Select argument
            arg[0] = tripTypeCursor.getString(codeIndex);

            do {
                // Create trip Type object
                tripType = new TripType(
                        tripTypeCursor.getInt(codeIndex),
                        tripTypeCursor.getString(descriptionIndex)
                );
                tripTypesList.add(tripType);
            }while (tripTypeCursor.moveToNext());
        }

        return tripTypesList;
    }

    public static void updateTripType(SQLiteDatabase db, TripType Type) {
        ContentValues values = new ContentValues();

        // Set values
        values.put(CODE, Type.getCode());
        values.put(DESCRIPTION, Type.getDescription());

        String whereClause = CODE + " = ?";
        String[] whereArg = new String[]{String.valueOf(Type.getCode())};

        // Add to local db
        long rowId = db.updateWithOnConflict(TRIP_TYPE_TABLE, values, whereClause, whereArg, SQLiteDatabase.CONFLICT_REPLACE);
        if (rowId <= 0) {
            Log.e("SQLite", "fail to insert into student");
        }
    }
}
