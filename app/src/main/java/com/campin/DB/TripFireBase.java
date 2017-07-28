package com.campin.DB;

import android.net.sip.SipAudioCall;

import com.campin.Utils.PlannedTrip;
import com.campin.Utils.Trip;
import com.campin.Utils.TripComments;
import com.campin.Utils.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StreamDownloadTask;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Igor on 7/24/2017.
 * Updated by Danielle on 25/7/2017
 */

public class TripFireBase
{
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();

    public static void addTrip(Trip newTrip, final Model.SuccessListener listener){
        DatabaseReference myRef = database.getReference("Trips").child(String.valueOf(newTrip.getTripID()));
        myRef.setValue(newTrip.toMap());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { listener.onResult(true); }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onResult(false);
            }
        });
    }

    public static void getTripById(String id, final Model.GetTripListener listener)
    {
        DatabaseReference myRef = database.getReference("Trips").child(id);

        myRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                Trip trip = snapshot.getValue(Trip.class);
                listener.onComplete(trip);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                listener.onComplete(null);
            }
        });
    }

    public static void getTripsFromDate(double lastUpdateDate, final Model.GetAllTripsListener listener) {
        final int[] maxKey = {-1};

        // Get all the desserts from the last update
        final DatabaseReference myRef = database.getReference("Trips");
        Query query = myRef.orderByChild("lastUpdated").startAt(lastUpdateDate);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<Trip> tripsList = new LinkedList<Trip>();

                // Create the desserts list
                for (DataSnapshot dstSnapshot : dataSnapshot.getChildren()) {
                    Trip trip = dstSnapshot.getValue(Trip.class);


//                    List<String> test = (List<String>) dstSnapshot.child("seasons");
//                    trip.setTripSeasons((List<String>) dstSnapshot.child("seasons"));
//                    trip.setTripTypes((List<Integer>) myRef.child("types"));
//                    trip.setTripEquipment((List<String>) myRef.child("equipment"));
//                    trip.setTripComments((List<TripComments>) myRef.child("comments"));


                    if (maxKey[0] < trip.getTripID()) {
                        maxKey[0] = trip.getTripID();
                    }

                    tripsList.add(trip);
                }
                listener.onComplete(tripsList, maxKey[0]);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onCancel();
            }
        });
    }
}
