package com.campin.Activities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.campin.DB.AreaSql;
import com.campin.DB.Model;
import com.campin.DB.ModelFireBase;
import com.campin.DB.ModelSql;
import com.campin.R;
import com.campin.Utils.Area;

import com.campin.Utils.Trip;
import com.campin.Utils.TripComments;
import com.google.firebase.appindexing.Action;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.appindexing.builders.Actions;

public class CreateTripActivity extends AppCompatActivity implements OnClickListener {
    private EditText _txtOptDate1;
    private EditText _txtOptDate2;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog optDatePickerDialog1;
    private DatePickerDialog optDatePickerDialog2;
    private Spinner _spnChooseArea;
    private FloatingActionButton fab;
    private Button _btnAddFriends;
    private TextView _tvFriends;

    // Boolean array for initial selected items
    public boolean[] checkedColors = new boolean[]
            {
                    false, // Red
                    false, // Green
                    false, // Blue
                    false, // Purple
                    false // Olive

            };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);

        if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_trip);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Collapsing Toolbar layout to the screen

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_create_trip);
        // Set title of Detail page
        collapsingToolbar.setTitle(getString(R.string.create_trip_title));

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        findViewsById();

        setDateTimeField();

        setArea();

        inviteFriends();


    }

    private void findViewsById()
    {
        _txtOptDate1 = (EditText) findViewById(R.id.txtlOptDate1);
        _txtOptDate1.requestFocus();

        _txtOptDate2 = (EditText) findViewById(R.id.txtOptDate2);

        _spnChooseArea = (Spinner) findViewById(R.id.spnChooseArea);

        // Adding Floating Action Button to bottom right of main view
        fab = (FloatingActionButton) findViewById(R.id.fab_create_trip);

        //_btnAddFriends = (Button) findViewById(R.id.btnAddFriends);

       // _tvFriends = (TextView) findViewById(R.id.tv);

        //_ctvInviteFriends = (CheckedTextView) findViewById(R.id.ctvIviteFriends);
    }

    private void inviteFriends()
    {
        // Adding Floating Action Button to bottom right of main view
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(v.getContext(), AddFriendsActivity.class);
                intent.putExtra("firstDate", _txtOptDate1.getText().toString());
                intent.putExtra("secDate", _txtOptDate2.getText().toString());
                intent.putExtra("area", _spnChooseArea.getSelectedItem().toString());
                startActivity(intent);
            }
        });
    }


    private void setArea()
    {

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.places, android.R.layout.simple_spinner_item);


        List<Area> allAreas = MainActivity.ar;
        String[] strAreaNames = new String[allAreas.size()];

        int i = 0;

        for(Area area: allAreas)
        {
            strAreaNames[i] = area.getDescription();

                    i++;
        }

        ArrayAdapter  aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, strAreaNames);

        // Specify the layout to use when the list of choices appears
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        // Apply the adapter to the spinner
        _spnChooseArea.setAdapter(aa);

    }

    private void setDateTimeField()
    {
        _txtOptDate1.setOnClickListener(this);
        _txtOptDate2.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();

        optDatePickerDialog1 = new DatePickerDialog(this, new OnDateSetListener()
        {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                _txtOptDate1.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        optDatePickerDialog2 = new DatePickerDialog(this, new OnDateSetListener()
        {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                _txtOptDate2.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View view)
    {
        if(view == _txtOptDate1)
        {
            optDatePickerDialog1.show();
        }
        else if(view == _txtOptDate2)
        {
            optDatePickerDialog2.show();
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        return Actions.newView("CreateTrip", "http://[ENTER-YOUR-URL-HERE]");
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        FirebaseUserActions.getInstance().start(getIndexApiAction());
    }

    @Override
    public void onStop() {

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        FirebaseUserActions.getInstance().end(getIndexApiAction());
        super.onStop();
    }

    private class ColorVO {
        private String name;
        private boolean selected;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }
}
