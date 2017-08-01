package com.campin.DB;

import com.campin.Utils.TripType;
import com.campin.Utils.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by noam on 03/06/2017.
 */

public class UserFireBase {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();

    public static void userLogIn(final User user, final Model.LogInListener listener)
    {
        DatabaseReference myRef = database.getReference("users").child(String.valueOf(user.getId()));

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User readUser = dataSnapshot.getValue(User.class);

                if ((readUser != null)) {
                    Model.instance().setConnectedUser(readUser);
                    listener.onComplete(true);
                } else {
                    listener.onComplete(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onComplete(false);
            }

        });
    }
    public static void userSignUp(User user, final Model.SignUpListener listener) {
        DatabaseReference myRef = database.getReference("users").child(user.getId());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User readUser = dataSnapshot.getValue(User.class);

                if (readUser == null) {
                    listener.onComplete(false);
                } else {
                    listener.onComplete(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onComplete(true);
            }
        });
    }

    public static void addUser(User user) {
        DatabaseReference myRef = database.getReference("users").child(user.getId());
        myRef.setValue(user.toMap());
    }


    public static void isUserExist(final String userId, final Model.SuccessListener listener) {
        DatabaseReference myRef = database.getReference("users").child(userId);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User readUser = dataSnapshot.getValue(User.class);

                if (readUser == null) {
                    listener.onResult(false);
                } else {
                    listener.onResult(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onResult(false);
            }

        });
    }

    public static User getUserById(String id, final Model.GetUserListener listener) {
        final User[] currUser = {null};
        DatabaseReference myRef = database.getReference("users").child(id);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                currUser[0] = user;
                listener.onComplete(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

       return  currUser[0];
    }

    public static void getUserFromDate(double lastUpdateDate, final Model.GetAllUserListener listener) {

        // Get all the desserts from the last update
        final DatabaseReference myRef = database.getReference("users");
        Query query = myRef.orderByChild("lastUpdated").startAt(lastUpdateDate);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<User> usersList = new LinkedList<User>();

                // Create the desserts list
                for (DataSnapshot dstSnapshot : dataSnapshot.getChildren()) {
                    User user = dstSnapshot.getValue(User.class);

                    usersList.add(user);
                }
                listener.onComplete(usersList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onCancel();
            }
        });
    }
}
