package com.campin.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.campin.Utils.Trip;
import com.campin.Utils.TripComments;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Danielle Cohen on 25/07/2017.
 */

public class TripSql {
    // Trip Table
    private static final String TRIPS_TABLE = "TRIPS";
    private static final String TRIP_ID = "ID";
    private static final String NAME = "NAME";
    private static final String AREA = "AREA";
    private static final String FRIENDS = "FRIENDS";
    private static final String DETAILS = "DETAILS";
    private static final String LEVEL = "LEVEL";
    private static final String IMAGE_URL = "IMAGE_URL";

    // Trip seasons Table
    private static final String TRIP_SEASONS_TABLE = "TRIP_SEASONS";
    private static final String SEASON = "SEASON";

    // Trip seasons Table
    private static final String TRIP_TYPES_TABLE = "TRIP_TYPES";
    private static final String TYPE = "TYPE";

    // Trip equipment Table
    private static final String TRIP_EQUIPMENT_TABLE = "TRIP_EQUIPMENT";
    private static final String EQUIPMENT = "EQUIPMENT";

    // Trip comments Table
    private  static final String TRIP_COMMENTS_TABLE = "TRIP_COMMENTS";
    private static final String COMMENT_ID = "COMMENT_ID";
    private static final String COMMENT = "COMMENT";
    private static final String SCORE = "SCORE";

    public static void createTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TRIPS_TABLE + " (" +
                TRIP_ID + " NUM PRIMARY KEY," +
                NAME + " TEXT," +
                AREA + " NUM," +
                FRIENDS + " NUM," +
                DETAILS + " TEXT," +
                LEVEL + " NUM," +
                IMAGE_URL + " TEXT );");

        db.execSQL("CREATE TABLE " + TRIP_SEASONS_TABLE + " (" +
                TRIP_ID + " NUM ," +
                SEASON + " TEXT, PRIMARY KEY ( " +
                TRIP_ID + ", " + SEASON + " ) );");

        db.execSQL("CREATE TABLE " + TRIP_TYPES_TABLE + " (" +
                TRIP_ID + " NUM ," +
                TYPE + " NUM, PRIMARY KEY ( " +
                TRIP_ID + ", " + TYPE + " ) );");

        db.execSQL("CREATE TABLE " + TRIP_EQUIPMENT_TABLE + " (" +
                TRIP_ID + " NUM ," +
                EQUIPMENT + " TEXT, PRIMARY KEY ( " +
                TRIP_ID + ", " + EQUIPMENT + " ) );");

        db.execSQL("CREATE TABLE " + TRIP_COMMENTS_TABLE + " (" +
                TRIP_ID + " NUM ," +
                COMMENT_ID + " NUM, " +
                COMMENT + " TEXT, " +
                SCORE + " NUM, PRIMARY KEY ( " +
                TRIP_ID + ", " + COMMENT_ID + " ) );");
    }

    public static void dropTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE " + TRIPS_TABLE);
        db.execSQL("DROP TABLE " + TRIP_SEASONS_TABLE);
        db.execSQL("DROP TABLE " + TRIP_TYPES_TABLE);
        db.execSQL("DROP TABLE " + TRIP_EQUIPMENT_TABLE);
        db.execSQL("DROP TABLE " + TRIP_COMMENTS_TABLE);
    }

    public static void addTrip(SQLiteDatabase db, Trip trip) {
        ContentValues values = new ContentValues();
        long rowId;

        // Set Trip table values
        values.put(TRIP_ID, trip.getId());
        values.put(NAME, trip.getName());
        values.put(AREA, trip.getArea());
        values.put(FRIENDS, trip.getFriendsNum());
        values.put(DETAILS, trip.getDetails());
        values.put(LEVEL, trip.getLevel());
        values.put(IMAGE_URL, trip.getImageUrl());

        // Add to local db
        rowId = db.insertWithOnConflict(TRIPS_TABLE, TRIP_ID, values, SQLiteDatabase.CONFLICT_REPLACE);
        if (rowId <= 0) {
            Log.e("SQLite", "fail to insert into trips");
        }

        // Set trip seasons table
        values.clear();

        for (String season : trip.getSeasons()){
            values.put(TRIP_ID, trip.getId());
            values.put(SEASON, season);

            // Add to local db
            rowId = db.insertWithOnConflict(TRIP_SEASONS_TABLE, TRIP_ID, values, SQLiteDatabase.CONFLICT_REPLACE);
            if (rowId <= 0) {
                Log.e("SQLite", "fail to insert into trip seasons");
            }
        }

        // Set trip types table
        values.clear();

        for (Integer type : trip.getTypes()){
            values.put(TRIP_ID, trip.getId());
            values.put(TYPE, type);

            // Add to local db
            rowId = db.insertWithOnConflict(TRIP_TYPES_TABLE, TRIP_ID, values, SQLiteDatabase.CONFLICT_REPLACE);
            if (rowId <= 0) {
                Log.e("SQLite", "fail to insert into trip types");
            }
        }

        // Set trip equipment table
        values.clear();

        for (String type : trip.getEquipment()){
            values.put(TRIP_ID, trip.getId());
            values.put(EQUIPMENT, type);

            // Add to local db
            rowId = db.insertWithOnConflict(TRIP_EQUIPMENT_TABLE, TRIP_ID, values, SQLiteDatabase.CONFLICT_REPLACE);
            if (rowId <= 0) {
                Log.e("SQLite", "fail to insert into trip equipment");
            }
        }

        // Set trip comments table
        values.clear();

        for (TripComments comment : trip.getComments()){
            values.put(TRIP_ID, trip.getId());
            values.put(COMMENT_ID, comment.get_commentId());
            values.put(COMMENT, comment.get_tripComment());
            values.put(SCORE, comment.get_commentScore());

            // Add to local db
            rowId = db.insertWithOnConflict(TRIP_COMMENTS_TABLE, TRIP_ID, values, SQLiteDatabase.CONFLICT_REPLACE);
            if (rowId <= 0) {
                Log.e("SQLite", "fail to insert into trip comments");
            }
        }
    }

    public static Trip getTripByID(SQLiteDatabase db, int id) {
        // Set the selection parameters
        String[] selectArg = {String.valueOf(id)};
        Trip trip = null;
        List<String> selectedStringValues = new ArrayList<String>();
        List<Integer> selectedIntegerdValues = new ArrayList<Integer>();
        List<TripComments> selectedComments = new ArrayList<TripComments>();

        // TRIP
        Cursor cursor = db.query(TRIPS_TABLE, null, TRIP_ID + " = ?", selectArg, null, null, null);

        if (cursor.moveToFirst() == true) {
            trip = new Trip(
                    cursor.getInt(cursor.getColumnIndex(TRIP_ID)),
                    cursor.getString(cursor.getColumnIndex(NAME)),
                    cursor.getInt(cursor.getColumnIndex(AREA)),
                    cursor.getInt(cursor.getColumnIndex(FRIENDS)),
                    cursor.getString(cursor.getColumnIndex(DETAILS)),
                    cursor.getInt(cursor.getColumnIndex(LEVEL)),
                    cursor.getString(cursor.getColumnIndex(IMAGE_URL)));

            trip.setSeasons(getSeasonsOfTripId(db, selectArg));
            trip.setTypes(getTypesOfTripId(db, selectArg));
            trip.setEquipment(getEquipmentOfTripId(db, selectArg));
            trip.setComments(getCommentsOfTripId(db, selectArg));
        }
        return trip;
    }

    public static double getLastUpdateDate(SQLiteDatabase db) {
        return LastUpdateSql.getLastUpdate(db, TRIPS_TABLE);
    }

    public static void setLastUpdateDate(SQLiteDatabase db, double date) {
        LastUpdateSql.setLastUpdate(db, TRIPS_TABLE, date);
    }

    public static List<Trip> getAllTrips(SQLiteDatabase db) {
        List<Trip> tripsList = null;
        String[] arg = new String[1];
        Trip trip;

        Cursor tripsCursor = db.query(TRIPS_TABLE, null, null, null, null, null, null);

        // If there is trips
        if (tripsCursor.moveToFirst()){
            // Defined indexes
            int idIndex = tripsCursor.getColumnIndex(TRIP_ID);
            int nameIndex = tripsCursor.getColumnIndex(NAME);
            int areaIndex = tripsCursor.getColumnIndex(AREA);
            int friendsIndex = tripsCursor.getColumnIndex(FRIENDS);
            int detailsIndex = tripsCursor.getColumnIndex(DETAILS);
            int levelIndex = tripsCursor.getColumnIndex(LEVEL);
            int imageUrlIndex = tripsCursor.getColumnIndex(IMAGE_URL);

            // Select argument
            arg[0] = tripsCursor.getString(idIndex);

            do {
                // Create trip object
                trip = new Trip(
                        tripsCursor.getInt(idIndex),
                        tripsCursor.getString(nameIndex),
                        tripsCursor.getInt(areaIndex),
                        getSeasonsOfTripId(db, arg),
                        getTypesOfTripId(db, arg),
                        getEquipmentOfTripId(db, arg),
                        getCommentsOfTripId(db, arg),
                        tripsCursor.getInt(friendsIndex),
                        tripsCursor.getString(detailsIndex),
                        tripsCursor.getInt(levelIndex),
                        tripsCursor.getString(imageUrlIndex)
                );
                tripsList.add(trip);
            }while (tripsCursor.moveToNext());
        }

        return tripsList;
    }

    private static List<String> getSeasonsOfTripId(SQLiteDatabase db, String[] selectArg){
        List<String> seasonsOfTrip = new ArrayList<String>();
        Cursor cursor = db.query(TRIP_SEASONS_TABLE, null, TRIP_ID + " = ?", selectArg, null, null, null);

        while (cursor.moveToNext()){
            seasonsOfTrip.add(cursor.getString((cursor.getColumnIndex(SEASON))));
        }

        return seasonsOfTrip;
    }

    private static List<Integer> getTypesOfTripId(SQLiteDatabase db, String[] selectArg){
        List<Integer> typesOfTrip = new ArrayList<Integer>();
        Cursor cursor = db.query(TRIP_TYPES_TABLE, null, TRIP_ID + " = ?", selectArg, null, null, null);

        while (cursor.moveToNext()){
            typesOfTrip.add(cursor.getInt((cursor.getColumnIndex(TYPE))));
        }

        return typesOfTrip;
    }

    private static List<String> getEquipmentOfTripId(SQLiteDatabase db, String[] selectArg){
        List<String> equipmentOfTrip = new ArrayList<String>();
        Cursor cursor = db.query(TRIP_EQUIPMENT_TABLE, null, TRIP_ID + " = ?", selectArg, null, null, null);

        while (cursor.moveToNext()){
            equipmentOfTrip.add(cursor.getString((cursor.getColumnIndex(EQUIPMENT))));
        }

        return equipmentOfTrip;
    }

    private static List<TripComments> getCommentsOfTripId(SQLiteDatabase db, String[] selectArg){
        List<TripComments> commentsOfTrip = new ArrayList<TripComments>();
        Cursor cursor = db.query(TRIP_COMMENTS_TABLE, null, TRIP_ID + " = ?", selectArg, null, null, null);

        while (cursor.moveToNext()){
            commentsOfTrip.add(
                    new TripComments(
                            cursor.getInt(cursor.getColumnIndex(COMMENT_ID)),
                            cursor.getString(cursor.getColumnIndex(COMMENT)),
                            cursor.getDouble(cursor.getColumnIndex(SCORE))
                    )
            );
        }

        return commentsOfTrip;
    }

    public static void updateTrip(SQLiteDatabase db, Trip trip) {
        // TODO
    }
}
