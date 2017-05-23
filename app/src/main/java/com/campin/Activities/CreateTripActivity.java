package com.campin.Activities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.campin.R;

public class CreateTripActivity extends AppCompatActivity implements OnClickListener {
    private EditText _txtOptDate1;
    private EditText _txtOptDate2;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog optDatePickerDialog1;
    private DatePickerDialog optDatePickerDialog2;
    private Spinner _spnChooseArea;
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

        _btnAddFriends = (Button) findViewById(R.id.btnAddFriends);

        _tvFriends = (TextView) findViewById(R.id.tv);

        //_ctvInviteFriends = (CheckedTextView) findViewById(R.id.ctvIviteFriends);
    }

    private void inviteFriends()
    {
        final ArrayList<ColorVO> colorList = new ArrayList<ColorVO>();



        _btnAddFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Intent intCreateTrip = new Intent(CreateTrip.this, AddFriends.class);
                //startActivity(intCreateTrip);


                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateTripActivity.this);



                // String array for alert dialog multi choice items
                final String[] colors = new String[]{
                        "Igor",
                        "Danielle",
                        "Noam",
                        "Adi",
                        "Liel"
                };



                // Convert the color array to list
                final List<String> colorsList = Arrays.asList(colors);

                // Set multiple choice items for alert dialog
                /*
                    AlertDialog.Builder setMultiChoiceItems(CharSequence[] items, boolean[]
                    checkedItems, DialogInterface.OnMultiChoiceClickListener listener)
                        Set a list of items to be displayed in the dialog as the content,
                        you will be notified of the selected item via the supplied listener.
                 */
                /*
                    DialogInterface.OnMultiChoiceClickListener
                    public abstract void onClick (DialogInterface dialog, int which, boolean isChecked)

                        This method will be invoked when an item in the dialog is clicked.

                        Parameters
                        dialog The dialog where the selection was made.
                        which The position of the item in the list that was clicked.
                        isChecked True if the click checked the item, else false.
                 */

                // make a list to hold state of every color
                for (int i = 0; i < colors.length; i++)
                {
                    ColorVO colorVO = new ColorVO();
                    colorVO.setName(colors[i]);
                    colorVO.setSelected(checkedColors[i]);
                    colorList.add(colorVO);
                }

                builder.setMultiChoiceItems(colors, checkedColors, new DialogInterface.OnMultiChoiceClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked)
                    {

                        // Update the current focused item's checked status
                        checkedColors[which] = isChecked;

                        // Get the current focused item
                        String currentItem = colorsList.get(which);

                        // Notify the current action
                        Toast.makeText(getApplicationContext(),
                                currentItem + " " + isChecked, Toast.LENGTH_SHORT).show();
                    }
                });



                // Specify the dialog is not cancelable
                builder.setCancelable(false);

                // Set a title for alert dialog
                builder.setTitle("Who Come's ?");

                // Set the positive/yes button click listener
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // Do something when click positive button
                        //_tvFriends.setText("Who Come's ? \n");

                        // make a list to hold state of every color
                        for (int i = 0; i < colors.length; i++)
                        {
                            ColorVO colorVO = new ColorVO();
                            colorVO.setName(colors[i]);
                            colorVO.setSelected(checkedColors[i]);
                            colorList.add(colorVO);
                        }



                        /*
                        for (int i = 0; i<checkedColors.length; i++)
                        {
                            boolean checked = checkedColors[i];
                            if (checked)
                            {
                                _tvFriends.setText(_tvFriends.getText() + colorsList.get(i) + "\n");
                            }
                        }*/

                        // colorList.clear();

                    }
                });

                // Set the negative/no button click listener
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when click the negative button
                        colorList.clear();
                    }
                });

                // Set the neutral/cancel button click listener
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when click the neutral button
                        colorList.clear();
                    }
                });

                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();
            }
        });
    }


    private void setArea()
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.places, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        _spnChooseArea.setAdapter(adapter);
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
