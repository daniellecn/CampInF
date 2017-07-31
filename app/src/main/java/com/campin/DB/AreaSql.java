package com.campin.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.campin.Utils.Area;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Danielle Cohen on 28/07/2017.
 */

public class AreaSql {
    // Trip levels Table
    private static final String AREA_TABLE = "AREA_TABLE";
    private static final String CODE = "CODE";
    private static final String DESCRIPTION = "DESCRIPTION";

    public static void createTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + AREA_TABLE + " (" +
                CODE + " NUM PRIMARY KEY," +
                DESCRIPTION + " TEXT );");
    }

    public static void dropTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE " + AREA_TABLE);
    }

    public static void addArea(SQLiteDatabase db, Area area) {
        ContentValues values = new ContentValues();
        long rowId;

        // Set Trip levels table values
        values.put(CODE, area.getCode());
        values.put(DESCRIPTION, area.getDescription());

        // Add to local db
        rowId = db.insertWithOnConflict(AREA_TABLE, CODE, values, SQLiteDatabase.CONFLICT_REPLACE);
        if (rowId <= 0) {
            Log.e("SQLite", "fail to insert into trips");
        }
    }

    public static Area getAreaByCode(SQLiteDatabase db, int id) {
        // Set the selection parameters
        String[] selectArg = {String.valueOf(id)};
        Area area = null;

        // TRIP
        Cursor cursor = db.query(AREA_TABLE, null, CODE + " = ?", selectArg, null, null, null);

        if (cursor.moveToFirst() == true) {
            area = new Area(
                    cursor.getInt(cursor.getColumnIndex(CODE)),
                    cursor.getString(cursor.getColumnIndex(DESCRIPTION)));
        }
        return area;
    }

    public static double getLastUpdateDate(SQLiteDatabase db) {
        return LastUpdateSql.getLastUpdate(db, AREA_TABLE);
    }

    public static void setLastUpdateDate(SQLiteDatabase db, double date) {
        LastUpdateSql.setLastUpdate(db, AREA_TABLE, date);
    }

    public static List<Area> getAllAreas(SQLiteDatabase db) {
        List<Area> areasList = new LinkedList<>();
        Area area;

        Cursor areaCursor = db.query(AREA_TABLE, null, null, null, null, null, null);

        // If there is trips
        if (areaCursor.moveToFirst()){
            // Defined indexes
            int codeIndex = areaCursor.getColumnIndex(CODE);
            int descriptionIndex = areaCursor.getColumnIndex(DESCRIPTION);

            do {
                // Create trip level object
                area = new Area(
                        areaCursor.getInt(codeIndex),
                        areaCursor.getString(descriptionIndex)
                );
                areasList.add(area);
            }while (areaCursor.moveToNext());
        }

        return areasList;
    }

    public static void updateArea(SQLiteDatabase db, Area level) {
        ContentValues values = new ContentValues();

        // Set values
        values.put(CODE, level.getCode());
        values.put(DESCRIPTION, level.getDescription());

        String whereClause = CODE + " = ?";
        String[] whereArg = new String[]{String.valueOf(level.getCode())};

        // Add to local db
        long rowId = db.updateWithOnConflict(AREA_TABLE, values, whereClause, whereArg, SQLiteDatabase.CONFLICT_REPLACE);
        if (rowId <= 0) {
            Log.e("SQLite", "fail to insert into student");
        }
    }

}
