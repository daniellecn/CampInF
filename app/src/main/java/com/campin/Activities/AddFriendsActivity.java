package com.campin.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.campin.R;
import com.campin.Utils.CustomAdapter;
import com.campin.Utils.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddFriendsActivity extends AppCompatActivity {

    String value;
    String[] superStarNames = {"John Cena", "Randy Orton", "Triple H", "Roman Reign", "Sheamus"};
    ArrayList<String> friends_names = new ArrayList<String>();
    ArrayList<String> friends_id = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        // initiate a ListView
        ListView listView = (ListView) findViewById(R.id.lsvFriendList);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_friends);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("החברים שלי");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setUserFriends();

        // set the adapter to fill the data in ListView
        CustomAdapter customAdapter = new CustomAdapter(this, friends_names,friends_id);
        listView.setAdapter(customAdapter);
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

            friends_names.add(User.getInstance().getFullName());
            friends_id.add(User.getInstance().getUserId());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
