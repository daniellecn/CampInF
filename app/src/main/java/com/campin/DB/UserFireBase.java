package com.campin.DB;

import com.campin.Utils.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by noam on 03/06/2017.
 */

public class UserFireBase {

    public static void userLogIn(final User user, final Model.LogInListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(String.valueOf(user.getUserId()));

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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(user.getUserId());

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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(user.getUserId());
        myRef.setValue(user.toMap());
    }


    public static void isUserExist(final String userId, final Model.SuccessListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
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

    public static User getUserById(String id, final Model.GetUserByIdListener listener) {
        final User[] currUser = {null};
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(id);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                //Log.d("TAG", dessert.getName() + " - " + dessert.getId());
                currUser[0] = user;
                listener.onComplete(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

       return  currUser[0];
    }
}
