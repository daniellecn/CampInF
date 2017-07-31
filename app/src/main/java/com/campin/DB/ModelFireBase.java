package com.campin.DB;

import android.graphics.PorterDuff;

import com.campin.Utils.Area;
import com.campin.Utils.PlannedTrip;
import com.campin.Utils.TripLevel;
import com.campin.Utils.TripType;
import com.campin.Utils.User;
import com.campin.Utils.Trip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by noam on 03/06/2017.
 * Updated by Danielle on 25/07/2017
 */

public class ModelFireBase {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();

    public void userLogIn(User user, Model.LogInListener listener) {
        UserFireBase.userLogIn(user, listener);
    }

    public void userSignUp(final User user, final Model.SuccessListener listener) {
        UserFireBase.userSignUp(user, new Model.SignUpListener() {
            @Override
            public void onComplete(boolean isExist) {
                if (isExist && !User.isSignUp) {
                    listener.onResult(false);
                }
                else if (isExist && User.isSignUp)
                {
                    UserFireBase.addUser(user);
                    listener.onResult(true);
                }
                else {
                    UserFireBase.addUser(user);
                    listener.onResult(true);
                }
            }
        });
    }

    public void addPlannedTrip(PlannedTrip trip, Model.SuccessListener listener) {
        // Add the dessert to firebase databace
        PlannedTripFireBase.addPlannedTrip(trip, listener);
    }

    public void isUserExist(String userId, Model.SuccessListener listener) {
        UserFireBase.isUserExist(userId, listener);
    }

    public User getUserById(String id, final Model.GetUserByIdListener listener) {
       User currUser  = UserFireBase.getUserById(id, listener);

        return  currUser;
    }

    public void addTrip(Trip trip, final Model.SuccessListener listener){
        DatabaseReference myRef = database.getReference("Trips").child(String.valueOf(trip.getId()));
        myRef.setValue(trip.toMap());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onResult(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onResult(false);
            }
        });
    }

    public void addTripLevel(TripLevel level, final Model.SuccessListener listener){
        DatabaseReference myRef = database.getReference("TripLevel").child(String.valueOf(level.getCode()));
        myRef.setValue(level.toMap());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onResult(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onResult(false);
            }
        });
    }

    public void addTripType(TripType type, final Model.SuccessListener listener){
        DatabaseReference myRef = database.getReference("TripTypes").child(String.valueOf(type.getCode()));
        myRef.setValue(type.toMap());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onResult(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onResult(false);
            }
        });
    }

    public void addArea(Area area, final Model.SuccessListener listener){
        DatabaseReference myRef = database.getReference("Areas").child(String.valueOf(area.getCode()));
        myRef.setValue(area.toMap());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onResult(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onResult(false);
            }
        });
    }

    public void getTripById(String id, final Model.GetTripListener listener) {
        TripFireBase.getTripById(id, listener);
    }

    public void getTripLevelByCode(int code, final Model.GetTripLevelListener listener){
        TripLevelFireBase.getTripLevelByCode(code, listener);
    }

    public void getTripTypeByCode(int code, final Model.GetTripTypeListener listener){
        TripTypeFireBase.getTripTypeByCode(code, listener);
    }

    public void getAreaByCode(int code, final Model.GetAreaListener listener){
        AreaFireBase.getAreaByCode(code, listener);
    }

    public void getTripsFromDate(double lastUpdateDate, final Model.GetAllTripsListener listener){
        TripFireBase.getTripsFromDate(lastUpdateDate, listener);
    }

    public void getTripLevelsFromDate(double lastUpdateDate, final Model.GetAllTripLevelsListener listener){
        TripLevelFireBase.getTripLevelFromDate(lastUpdateDate, listener);
    }

    public void getTripTypeFromDate(double lastUpdateDate, final Model.GetAllTripTypesListener listener){
        TripTypeFireBase.getTripTypeFromDate(lastUpdateDate, listener);
    }

    public void getAreaFromDate(double lastUpdateDate, final Model.GetAllAreaListener listener){
        AreaFireBase.getAreaFromDate(lastUpdateDate, listener);
    }

}
