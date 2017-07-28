package com.campin.DB;

import com.campin.Utils.TripType;
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

public class TripTypeFireBase {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();

    public static void addTripType(TripType newTripType, final Model.SuccessListener listener){
        DatabaseReference myRef = database.getReference("TripTypes").child(String.valueOf(newTripType.getCode()));
        myRef.setValue(newTripType.toMap());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { listener.onResult(true); }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onResult(false);
            }
        });
    }

    public static void getTripTypeByCode(int id, final Model.GetTripTypeListener listener)
    {
        DatabaseReference myRef = database.getReference("TripType").child(String.valueOf(id));

        myRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                TripType tripType = snapshot.getValue(TripType.class);
                listener.onComplete(tripType);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                listener.onComplete(null);
            }
        });
    }

    public static void getTripTypeFromDate(double lastUpdateDate, final Model.GetAllTripTypesListener listener) {
        final int[] maxKey = {-1};

        // Get all the desserts from the last update
        final DatabaseReference myRef = database.getReference("TripTypes");
        Query query = myRef.orderByChild("lastUpdated").startAt(lastUpdateDate);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<TripType> tripTypeList = new LinkedList<TripType>();

                // Create the desserts list
                for (DataSnapshot dstSnapshot : dataSnapshot.getChildren()) {
                    TripType tripType = dstSnapshot.getValue(TripType.class);

                    if (maxKey[0] < tripType.getCode()) {
                        maxKey[0] = tripType.getCode();
                    }

                    tripTypeList.add(tripType);
                }
                listener.onComplete(tripTypeList, maxKey[0]);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onCancel();
            }
        });
    }
}
