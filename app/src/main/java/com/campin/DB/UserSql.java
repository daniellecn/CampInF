package com.campin.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.campin.Utils.Trip;
import com.campin.Utils.TripComments;
import com.campin.Utils.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Danielle Cohen on 01/08/2017.
 */

public class UserSql {
    private static final String USERS_TABLE = "USERS";
    private static final String ID = "ID";
    private static final String NAME = "NAME";
    private static final String EMAIL = "EMAIL";
    private static final String BIRTHDAY = "BIRTHDAY";
    private static final String LOCATION = "LOCATION";
    private static final String FRIENDS = "FRIENDS";
    private static final String CAR = "CAR";
    private static final String LEVEL = "LEVEL";
    private static final String PROFILE = "PROFILE";
    private static final String COVER = "COVER";
    private static final String IS_SHOW_FRIENDS = "IS_SHOW_FRIENDS";

    private static final String USER_AREA_TABLE = "USER_AREA";
    private static final String AREA_CODE = "AREA_CODE";
    private static final String USER_TYPE_TABLE = "USER_TYPE";
    private static final String TYPE_CODE = "TYPE_CODE";


    public static void createTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + USERS_TABLE + " (" +
                ID + " NUM PRIMARY KEY," +
                NAME + " TEXT," +
                EMAIL + " TEXT," +
                BIRTHDAY + " TEXT," +
                LOCATION + " TEXT," +
                FRIENDS + " TEXT," +
                CAR + " TEXT," +
                LEVEL + " TEXT," +
                COVER + " TEXT," +
                IS_SHOW_FRIENDS + " TEXT );");

        db.execSQL("CREATE TABLE " + USER_AREA_TABLE + " (" +
                ID + " NUM ," +
                AREA_CODE + " TEXT, PRIMARY KEY ( " +
                ID + ", " + AREA_CODE + " ) );");

        db.execSQL("CREATE TABLE " + USER_TYPE_TABLE + " (" +
                ID + " NUM ," +
                TYPE_CODE + " TEXT, PRIMARY KEY ( " +
                ID + ", " + TYPE_CODE + " ) );");
    }

    public static void dropTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE " + USERS_TABLE);
        db.execSQL("DROP TABLE " + USER_AREA_TABLE);
        db.execSQL("DROP TABLE " + USER_TYPE_TABLE);
    }

    public static void addUser(SQLiteDatabase db, User user) {
        ContentValues values = new ContentValues();
        long rowId;

        // Set user table values
        values.put(ID, user.getId());
        values.put(NAME, user.getName());
        values.put(EMAIL, user.getEmail());
        values.put(BIRTHDAY, user.getBirthday());
        values.put(LOCATION, user.getLocation());
        values.put(FRIENDS, user.getFriends());
        values.put(CAR, user.getIsCar());
        values.put(LEVEL, user.getLevel());
        values.put(COVER, user.getUrlCover());
        values.put(IS_SHOW_FRIENDS, user.isShowFriends());

        // Add to local db
        rowId = db.insertWithOnConflict(USERS_TABLE, ID, values, SQLiteDatabase.CONFLICT_REPLACE);
        if (rowId <= 0) {
            Log.e("SQLite", "fail to insert into trips");
        }

        // Set user area table
        values.clear();

        for (Integer area : user.getPreferedAreas()) {
            values.put(ID, user.getId());
            values.put(AREA_CODE, area);

            // Add to local db
            rowId = db.insertWithOnConflict(USER_AREA_TABLE, ID, values, SQLiteDatabase.CONFLICT_REPLACE);
            if (rowId <= 0) {
                Log.e("SQLite", "fail to insert into trip seasons");
            }
        }

        // Set user types table
        values.clear();

        for (Integer type : user.getPreferedTypes()) {
            values.put(ID, user.getId());
            values.put(TYPE_CODE, type);

            // Add to local db
            rowId = db.insertWithOnConflict(USER_TYPE_TABLE, ID, values, SQLiteDatabase.CONFLICT_REPLACE);
            if (rowId <= 0) {
                Log.e("SQLite", "fail to insert into trip seasons");
            }
        }
    }

    public static User getUserByID(SQLiteDatabase db, String id) {
        // Set the selection parameters
        String[] selectArg = {String.valueOf(id)};
        User user = null;

        Cursor cursor = db.query(USERS_TABLE, null, ID + " = ?", selectArg, null, null, null);

        if (cursor.moveToFirst() == true){
            user = new User(
                    cursor.getString(cursor.getColumnIndex(ID)),
                    cursor.getString(cursor.getColumnIndex(NAME)),
                    cursor.getString(cursor.getColumnIndex(EMAIL)),
                    cursor.getString(cursor.getColumnIndex(BIRTHDAY)),
                    cursor.getString(cursor.getColumnIndex(LOCATION)),
                    cursor.getString(cursor.getColumnIndex(FRIENDS)),
                    Boolean.valueOf(cursor.getInt(cursor.getColumnIndex(CAR)) > 0),
                    getAreaOfUserId(db, selectArg),
                    getTypesOfUserId(db, selectArg),
                    cursor.getInt(cursor.getColumnIndex(LEVEL)),
                    null,
                    cursor.getString(cursor.getColumnIndex(COVER)),
                    Boolean.valueOf(cursor.getString(cursor.getColumnIndex(IS_SHOW_FRIENDS))));
        }

        return user;
    }

    public static double getLastUpdateDate(SQLiteDatabase db) {
        return LastUpdateSql.getLastUpdate(db, USERS_TABLE);
    }

    public static void setLastUpdateDate(SQLiteDatabase db, double date) {
        LastUpdateSql.setLastUpdate(db, USERS_TABLE, date);
    }

    public static List<User> getAllUsers(SQLiteDatabase db) {
        List<User> usersList = new LinkedList<User>();
        String[] arg = new String[1];
        User user;

        Cursor usersCursor = db.query(USERS_TABLE, null, null, null, null, null, null);
        Cursor usersAreaCursor = db.query(USER_AREA_TABLE, null, null, null, null, null, null);
        Cursor usersTypeCursor = db.query(USER_TYPE_TABLE, null, null, null, null, null, null);

        // If there is trips
        if (usersCursor.moveToFirst()){
            // Defined indexes
            int idIndex = usersCursor.getColumnIndex(ID);
            int nameIndex = usersCursor.getColumnIndex(NAME);
            int emailIndex = usersCursor.getColumnIndex(EMAIL);
            int birthdayIndex = usersCursor.getColumnIndex(BIRTHDAY);
            int locationIndex = usersCursor.getColumnIndex(LOCATION);
            int friendsIndex = usersCursor.getColumnIndex(FRIENDS);
            int carIndex = usersCursor.getColumnIndex(CAR);
            int levelIndex = usersCursor.getColumnIndex(LEVEL);
//            int profileIndex = usersCursor.getColumnIndex(PROFILE);
            int coverIndex = usersCursor.getColumnIndex(COVER);
            int showFriendsIndex = usersCursor.getColumnIndex(IS_SHOW_FRIENDS);

            // Select argument
            arg[0] = usersCursor.getString(idIndex);

            do {
                // Create trip object
                user = new User(
                        usersCursor.getString(idIndex),
                        usersCursor.getString(nameIndex),
                        usersCursor.getString(emailIndex),
                        usersCursor.getString(birthdayIndex),
                        usersCursor.getString(locationIndex),
                        usersCursor.getString(locationIndex),
                        Boolean.valueOf(usersCursor.getString(carIndex)),
                        getAreaOfUserId(db, arg),
                        getTypesOfUserId(db, arg),
                        usersCursor.getInt(levelIndex),
                        null,
//                        usersCursor.getString(profileIndex).getBytes(),
                        usersCursor.getString(coverIndex),
                        Boolean.valueOf(usersCursor.getString(showFriendsIndex))
                );


                usersList.add(user);
            }while (usersCursor.moveToNext());
        }

        return usersList;
    }

    private static List<Integer> getTypesOfUserId(SQLiteDatabase db, String[] selectArg){
        List<Integer> typesOfUser = new ArrayList<Integer>();
        selectArg[0] = User.getInstance().getId();
        Cursor cursor = db.query(USER_TYPE_TABLE, null, ID + " = ?", selectArg, null, null, null);

        while (cursor.moveToNext()){
            typesOfUser.add(cursor.getInt((cursor.getColumnIndex(TYPE_CODE))));
        }

        return typesOfUser;
    }

    private static List<Integer> getAreaOfUserId(SQLiteDatabase db, String[] selectArg){
        List<Integer> areaOfUser = new ArrayList<Integer>();
        selectArg[0] = User.getInstance().getId();
        Cursor cursor = db.query(USER_AREA_TABLE, null, ID + " = ?", selectArg, null, null, null);

        while (cursor.moveToNext()){
            areaOfUser.add(cursor.getInt((cursor.getColumnIndex(AREA_CODE))));
        }

        return areaOfUser;
    }
}