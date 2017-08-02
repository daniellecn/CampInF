package com.campin.DB;

import android.widget.Toast;

import com.campin.Utils.PlannedTrip;
import com.campin.Utils.Trip;
import com.campin.Utils.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by noam on 03/06/2017.
 */

class PlannedTripFireBase {

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();

    public static void addPlannedTrip(PlannedTrip trip, final Model.SuccessListener listener) {

        //trip.setId("0");
       // getTripsUserBelongs(User.getInstance().getUserId(),listener);
        String tripId = database.getReference().child("plannedTrips").push().getKey();

        //   trip.setTripId(tripId);
        DatabaseReference myRef = database.getReference("plannedTrips").
                child(tripId);
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

    public static ArrayList<PlannedTrip> getTripsUserBelongs(final Model.getTripsUserBelongsListener listener) {
        final int[] maxKey = {-1};
        final ArrayList<PlannedTrip>  trips = new ArrayList<PlannedTrip>();
        DatabaseReference r = database.getReference("plannedTrips");
        r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot snap: snapshot.getChildren()) {
                    final PlannedTrip trip = snap.getValue(PlannedTrip.class);

                    ArrayList<String> friends = trip.getFriends();
                    if (friends != null)
                    {
                        if (friends.contains(User.getInstance().getId()) ||
                                trip.getCreator().equals(User.getInstance().getId()))
                        {
                            trips.add(trip);
                        }
                    }
                }

                //Log.d("TAG", dessert.getName() + " - " + dessert.getId());

                listener.onComplete(trips, maxKey[0]);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return  trips;
    }

    public static void getSuitableTripAlgo(PlannedTrip trip, final Model.getSuitableTrip listener)
    {
        DatabaseReference r = database.getReference("plannedTrips");
    }

}
