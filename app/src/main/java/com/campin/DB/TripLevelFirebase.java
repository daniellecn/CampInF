package com.campin.DB;

import com.campin.Utils.Trip;
import com.campin.Utils.TripLevel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Danielle Cohen on 28/07/2017.
 */

class TripLevelFireBase
{
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();

    public static void addTripLevel(TripLevel newTripLevel, final Model.SuccessListener listener){
        DatabaseReference myRef = database.getReference("Triplevel").child(String.valueOf(newTripLevel.getCode()));
        myRef.setValue(newTripLevel.toMap());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { listener.onResult(true); }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onResult(false);
            }
        });
    }

    public static void getTripLevelByCode(int id, final Model.GetTripLevelListener listener)
    {
        DatabaseReference myRef = database.getReference("TripLevel").child(String.valueOf(id));

        myRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                TripLevel tripLevel = snapshot.getValue(TripLevel.class);
                listener.onComplete(tripLevel);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                listener.onComplete(null);
            }
        });
    }

    public static void getTripLevelFromDate(double lastUpdateDate, final Model.GetAllTripLevelsListener listener) {
        final int[] maxKey = {-1};

        // Get all the desserts from the last update
        final DatabaseReference myRef = database.getReference("TripLevel");
        Query query = myRef.orderByChild("lastUpdated").startAt(lastUpdateDate);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<TripLevel> tripLevelList = new LinkedList<TripLevel>();

                // Create the desserts list
                for (DataSnapshot dstSnapshot : dataSnapshot.getChildren()) {
                    TripLevel tripLevel = dstSnapshot.getValue(TripLevel.class);

                    if (maxKey[0] < tripLevel.getCode()) {
                        maxKey[0] = tripLevel.getCode();
                    }

                    tripLevelList.add(tripLevel);
                }
                listener.onComplete(tripLevelList, maxKey[0]);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onCancel();
            }
        });
    }
}
