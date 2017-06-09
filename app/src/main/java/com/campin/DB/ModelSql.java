package com.campin.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.campin.Utils.PlannedTrip;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by noam on 03/06/2017.
 */

public class ModelSql {
    AppContext appContext = new AppContext();
    private SQLiteOpenHelper helper;
    private int version = 7;

    public ModelSql() {
        helper = new Helper(getApplicationContext());
        helper.onUpgrade(getWritableDB(), 7, 7);
    }

    public SQLiteDatabase getWritableDB() {
        return helper.getWritableDatabase();
    }

    public SQLiteDatabase getReadbleDB() {
        return helper.getReadableDatabase();
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

    public List<PlannedTrip> getAllDesserts() {
        return PlannedTripSql.getAllPlannedTrip(helper.getReadableDatabase());
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
            PlannedTripSql.create(db);
            LastUpdateSql.create(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            PlannedTripSql.dropTable(db);
            LastUpdateSql.drop(db);
            onCreate(db);
        }
    }
}
