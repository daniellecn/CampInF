package com.campin.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.campin.Utils.Area;
import com.campin.Utils.PlannedTrip;
import com.campin.Utils.Trip;
import com.campin.Utils.TripLevel;
import com.campin.Utils.TripType;
import com.campin.Utils.User;
import com.campin.Utils.Utils;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by noam on 03/06/2017.
 * Updated by Danielle on 25/07/2017
 */

public class ModelSql {
    private SQLiteOpenHelper helper;
    private int version = 7;

    public ModelSql() {
        helper = new Helper(getApplicationContext());
        helper.onUpgrade(getWritableDB(), 7, 7);
       // helper.onCreate(getReadableDB());
    }

    public SQLiteDatabase getWritableDB() {
        return helper.getWritableDatabase();
    }

    public SQLiteDatabase getReadableDB() {
        return helper.getReadableDatabase();
    }

    public void addTrip(Trip trip){
        TripSql.addTrip(helper.getWritableDatabase(), trip);
    }

    public void addTripLevel(TripLevel level){
        TripLevelSql.addTripLevel(helper.getWritableDatabase(), level);
    }

    public void addTripType(TripType type){
        TripTypeSql.addTripType(helper.getWritableDatabase(), type);
    }

    public void addArea(Area area){
        AreaSql.addArea(helper.getWritableDatabase(), area);
    }

    public void addUser(User user){
        UserSql.addUser(helper.getWritableDatabase(), user);
    }

    public Trip getTripById(String id){
        return TripSql.getTripByID(helper.getReadableDatabase(), id);
    }

    public TripLevel getTripLevelByCode(int code){
        return TripLevelSql.getTripLevelByCode(helper.getReadableDatabase(),code);
    }

    public TripType getTripTypeByCode(int code){
        return TripTypeSql.getTripTypeByCode(helper.getReadableDatabase(), code);
    }

    public Area getAreaByCode(int code){
        return AreaSql.getAreaByCode(helper.getReadableDatabase(), code);
    }

    public User getUserById(String id){
        return UserSql.getUserByID(helper.getReadableDatabase(), id);
    }

    public List<Trip> getAllTrips(){
        return TripSql.getAllTrips(helper.getReadableDatabase());
    }

    public List<TripLevel> getAllTripLevels(){
        return TripLevelSql.getAllTripLevels(helper.getReadableDatabase());
    }

    public List<TripType> getAllTripType(){
        return TripTypeSql.getAllTripTypes(helper.getReadableDatabase());
    }

    public List<Area> getAllArea(){
        return AreaSql.getAllAreas(helper.getReadableDatabase());
    }

    public List<User> getAllUsers(){
        return UserSql.getAllUsers(helper.getReadableDatabase());
    }

    public void addPlannedTrip(PlannedTrip trip) {
        PlannedTripSql.addTrip(helper.getWritableDatabase(), trip);
    }

    public void updatePlannedTrip(PlannedTrip trip) {
        PlannedTripSql.updateDessert(helper.getWritableDatabase(), trip);
    }

    public PlannedTrip getPlannedTripById(int id) {
        return PlannedTripSql.getDessertById(helper.getReadableDatabase(), String.valueOf(id));
    }

    public List<PlannedTrip> getBySearch(String query) {
        return PlannedTripSql.getDessertsBySearch(helper.getReadableDatabase(), query);
    }

    class Helper extends SQLiteOpenHelper {

        public Helper(Context context) {
            super(context, "database.db", null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            UserSql.createTable(db);
            TripSql.createTable(db);
            //PlannedTripSql.create(db);
            LastUpdateSql.create(db);
            AreaSql.createTable(db);
            TripTypeSql.createTable(db);
            TripLevelSql.createTable(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            UserSql.dropTable(db);
            TripSql.dropTable(db);
            //PlannedTripSql.dropTable(db);
            LastUpdateSql.drop(db);
            AreaSql.dropTable(db);
            TripTypeSql.dropTable(db);
            TripLevelSql.dropTable(db);
            onCreate(db);

//            db.execSQL("DROP TABLE CHECKIN_TABLE");
//            db.execSQL("CREATE TABLE TEST (ID NUMERIC, TRIP_ID NUMERIC, SEASON TEXT, PRIMARY KEY(ID));");
//
//            db.execSQL("CREATE TABLE CHECKIN_TABLE (" +
//                    "CHECKIN_TYPE TEXT NOT NULL," +
//                    "CHECKIN_TIME NUMERIC NOT NULL," +
//                    "CHECKIN_COUNT NUMERIC," +
//                    " PRIMARY KEY ( CHECKIN_TYPE , CHECKIN_TIME ));");
//
//            ContentValues values = new ContentValues();
//            values.put("CHECKIN_TYPE", "1");
//            values.put("CHECKIN_TIME", 1);
//            values.put("CHECKIN_COUNT", 1);
//            long rowId = db.insert("CHECKIN_TABLE", "CHECKIN_TYPE", values);
//            ContentValues values2 = new ContentValues();
//            values2.put("CHECKIN_TYPE", "1");
//            values2.put("CHECKIN_TIME", 2);
//            values2.put("CHECKIN_COUNT", 2);
//            rowId = db.insert("CHECKIN_TABLE", "CHECKIN_TYPE", values2);
//
//            String[] selectArg = {String.valueOf(1)};
//
//            Cursor cursor;
//            cursor = db.query("CHECKIN_TABLE", null, "CHECKIN_TYPE = ?",
//                    selectArg, null, null, null);
//            String type;
//            int time;
//            int count;
//            String type2;
//            int time2;
//            int count2;
//            int countRows = cursor.getCount();
//            int type_index = cursor.getColumnIndex("CHECKIN_TYPE");
//            int time_index = cursor.getColumnIndex("CHECKIN_TIME");
//            int count_index = cursor.getColumnIndex("CHECKIN_COUNT");
//            if (cursor.moveToFirst()) {
//                do{
//                type = cursor.getString(type_index);
//                time = cursor.getInt(time_index);
//                count = cursor.getInt(count_index);
//                } while (cursor.moveToNext());
////                boolean b = cursor.moveToNext();
////                type2 = cursor.getString(type_index);
////                time2 = cursor.getInt(time_index);
////                count2 = cursor.getInt(count_index);
//                int a = 3;
//            }
////            }
//
//////            Cursor cursor = db.query("CHECKIN_TABLE", null, "CHECKIN_TYPE = ?", selectArg, null, null, null);
////            String id2;
////            int id;
////            String season;
////            while (cursor.moveToNext()){
////                id2 = String.valueOf(cursor.getColumnIndex("CHECKIN_TYPE"));
////                id = cursor.getColumnIndex("CHECKIN_TIME");
////                season = String.valueOf(cursor.getColumnIndex("CHECKIN_COUNT"));
////                int a = 0;
////            }

        }
    }
}
