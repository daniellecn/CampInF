package com.campin.DB;

import com.campin.Utils.PlannedTrip;
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
        DatabaseReference myRef = database.getReference("Trips").child(String.valueOf(trip.getTripID()));
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

    public void getTripById(String id, final Model.GetTripListener listener) {
        TripFireBase.getTripById(id, listener);
    }

    public void getTripsFromDate(double lastUpdateDate, final Model.GetAllTripsListener listener){
        TripFireBase.getTripsFromDate(lastUpdateDate, listener);
    }

}
