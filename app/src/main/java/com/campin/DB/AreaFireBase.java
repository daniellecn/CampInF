package com.campin.DB;

import com.campin.Utils.Area;
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

public class AreaFireBase {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();

    public static void addArea(Area newArea, final Model.SuccessListener listener){
        DatabaseReference myRef = database.getReference("Areas").child(String.valueOf(newArea.getCode()));
        myRef.setValue(newArea.toMap());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { listener.onResult(true); }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onResult(false);
            }
        });
    }

    public static void getAreaByCode(int id, final Model.GetAreaListener listener)
    {
        DatabaseReference myRef = database.getReference("Areas").child(String.valueOf(id));

        myRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                Area area = snapshot.getValue(Area.class);
                listener.onComplete(area);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                listener.onComplete(null);
            }
        });
    }

    public static void getAreaFromDate(double lastUpdateDate, final Model.GetAllAreaListener listener) {
        final int[] maxKey = {-1};

        // Get all the desserts from the last update
        final DatabaseReference myRef = database.getReference("Areas");
        Query query = myRef.orderByChild("lastUpdated").startAt(lastUpdateDate);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<Area> areaList = new LinkedList<Area>();

                // Create the desserts list
                for (DataSnapshot dstSnapshot : dataSnapshot.getChildren()) {
                    Area area = dstSnapshot.getValue(Area.class);

                    if (maxKey[0] < area.getCode()) {
                        maxKey[0] = area.getCode();
                    }

                    areaList.add(area);
                }
                listener.onComplete(areaList, maxKey[0]);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onCancel();
            }
        });
    }
}
