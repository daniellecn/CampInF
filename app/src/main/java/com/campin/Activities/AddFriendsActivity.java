package com.campin.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.campin.DB.Model;
import com.campin.R;
import com.campin.Adapters.CustomAdapter;
import com.campin.Utils.PlannedTrip;
import com.campin.Utils.Trip;
import com.campin.Utils.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddFriendsActivity extends AppCompatActivity {

    ArrayList<String> friends_names = new ArrayList<String>();
    ArrayList<String> friends_id = new ArrayList<String>();
    PlannedTrip newPlannedTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        TextView firstDate = (TextView) findViewById(R.id.firstOptionDate);
        firstDate.setText(getIntent().getStringExtra("firstDate"));
        TextView secDate = (TextView) findViewById(R.id.secOptionDate);
        secDate.setText(getIntent().getStringExtra("secDate"));

        TextView tripHead = (TextView) findViewById(R.id.trip_detail_head);
        tripHead.setText("פרטי הטיול ל" + getIntent().getStringExtra("area"));

        Trip t = new Trip(null,getIntent().getStringExtra("area"));

        newPlannedTrip = new PlannedTrip
                (t ,User.getInstance().getUserId(),
                        getIntent().getStringExtra("firstDate").toString(),getIntent().getStringExtra("secDate").toString());
        if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        // initiate a ListView
        final ListView listView = (ListView) findViewById(R.id.lsvFriendList);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_friends);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("החברים שלי");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setUserFriends();

        // set the adapter to fill the data in ListView
        final CustomAdapter customAdapter = new CustomAdapter(this, friends_names,friends_id);
        listView.setAdapter(customAdapter);

        // Adding Floating Action Button to bottom right of main view
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_friends);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Trip newTrip = new Trip();
                newPlannedTrip.setFriendInTrip(customAdapter._friends);
                showSuccessDialog();
               // final Intent intent = new Intent(v.getContext(), MainActivity.class);
               // startActivity(intent);
            }
        });
    }

    private void setUserFriends()
    {
        friends_id = new ArrayList<String>();
        friends_names = new ArrayList<String>();

        User.getInstance().setShowFriends(true);
        try {

            JSONArray jsonFriends = new JSONArray(User.getInstance().getFriends());

            for (int i = 0; i < jsonFriends.length(); i++) {

                JSONObject friend = (JSONObject) jsonFriends.get(i);
                friends_names.add(friend.getString("name"));
                friends_id.add(friend.getString("id"));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void showSuccessDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(AddFriendsActivity.this
                ,R.style.AppTheme_Dark_Dialog);
        AlertDialog dialog;
        builder.setTitle("הטיול ל" + newPlannedTrip.getTrip().getArea() + " נוצר בהצלחה");
        builder.setIcon(R.drawable.checked);

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });


        dialog = builder.create();
        // display dialog
        dialog.show();

        // adding to the db

        Model.instance().addPlannedTrip(newPlannedTrip, new Model.SuccessListener() {
            @Override
            public void onResult(boolean result) {
                if (result) {
                    // Display message
                    Toast.makeText(getApplicationContext(),
                            "התעדכן בהצלחה",
                            Toast.LENGTH_SHORT).show();

                    // Return to the list activity
                    Intent intent = new Intent(AddFriendsActivity.this,
                            MainActivity.class);
                    startActivity(intent);
                } else {
                    // Display message
                    Toast.makeText(getApplicationContext(),
                           "התרחשה שגיאה",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
