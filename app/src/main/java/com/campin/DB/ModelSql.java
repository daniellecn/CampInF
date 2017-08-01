package com.campin.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.campin.Utils.Area;
import com.campin.Utils.PlannedTrip;
import com.campin.Utils.Trip;
import com.campin.Utils.TripLevel;
import com.campin.Utils.TripType;
import com.campin.Utils.User;

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
       //helper.onCreate(getReadableDB());
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
        }
    }
}
