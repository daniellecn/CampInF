package com.campin.DB;

import android.net.sip.SipAudioCall;

import com.campin.Utils.PlannedTrip;
import com.campin.Utils.Trip;
import com.campin.Utils.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Igor on 7/24/2017.
 */

public class TripFireBase
{
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();

    public static Trip getTripById(String id, final Model.GetTripByIdListener listener)
    {
        final Trip[] currTrip = {null};
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Trips").child(id);

        myRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                Trip trip = snapshot.getValue(Trip.class);
                currTrip[0] = trip;
                listener.onComplete(trip);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

        return  currTrip[0];

    }
}
