package com.campin.DB;

import com.campin.Utils.PlannedTrip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by noam on 03/06/2017.
 */

class PlannedTripFireBase {

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();

    public static void addPlannedTrip(PlannedTrip trip, final Model.SuccessListener listener) {

        DatabaseReference myRef = database.getReference("plannedTrips").
                child(String.valueOf(trip.getTripId()));
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
}
