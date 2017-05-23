package com.campin.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.campin.R;
import com.campin.Utils.CustomAdapter;

public class AddFriendsActivity extends AppCompatActivity {

    String value;
    String[] superStarNames = {"John Cena", "Randy Orton", "Triple H", "Roman Reign", "Sheamus"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
        // initiate a ListView
        ListView listView = (ListView) findViewById(R.id.lsvFriendList);
        // set the adapter to fill the data in ListView
       // CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), areas);
      //  listView.setAdapter(customAdapter);
    }

}
