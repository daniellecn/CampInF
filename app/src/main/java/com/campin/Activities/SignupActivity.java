package com.campin.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.campin.DB.Model;
import com.campin.R;
import com.campin.Adapters.CustomAdapter;
import com.campin.Utils.Area;
import com.campin.Utils.TripLevel;
import com.campin.Utils.TripType;
import com.campin.Utils.User;

import java.util.ArrayList;
import java.util.List;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    String value;
    CustomAdapter customAdapter;
    typesAdapter typeAdapter;
    levelAdapter levelAdapter;

    ArrayList<String> areas = new ArrayList<String>();
    ArrayList<String> id = new ArrayList<>();
    List<TripType> _types = new ArrayList<TripType>();
    List<TripLevel> _levels = new ArrayList<TripLevel>();
    List<Area> _areas = new ArrayList<Area>();
    ListView levelListView;
    ListView tripTypesllistView;

    @InjectView(R.id.btn_signup) Button _signupButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        User.getInstance().setShowFriends(false);
        
        if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_signup);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("הגדרות משתמש");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.inject(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });


        loadData();


        // initiate a ListView
        ListView listView = (ListView) findViewById(R.id.listView);
        // initiate a ListView
        tripTypesllistView = (ListView) findViewById(R.id.typeListView);
        levelListView = (ListView) findViewById(R.id.levelListView);

        typeAdapter = new typesAdapter();
        levelAdapter = new levelAdapter();

        tripTypesllistView.setAdapter(typeAdapter);
        levelListView.setAdapter(levelAdapter);


        // set the adapter to fill the data in ListView
        customAdapter = new CustomAdapter(this, areas, id,_types,_levels);
        listView.setAdapter(customAdapter);

        // Checking if there are prefered areas.
        if (!User.getInstance().getPreferedAreas().isEmpty())
        {
            customAdapter._preferedAreas = User.getInstance().getPreferedAreas();
        }

    }

    private void loadData()
    {
        Model.instance().getAllAreaAsynch(new Model.GetAllAreaListener() {
            @Override
            public void onComplete(List<Area> areaList, int currentMaxKey)
            {
                for (Area a : areaList)
                {
                    areas.add(a.getDescription());
                    id.add(String.valueOf(a.getCode()));
                }

            }

            @Override
            public void onCancel() {

            }
        });

        Model.instance().getAllTripLevelsAsynch(new Model.GetAllTripLevelsListener() {
            @Override
            public void onComplete(List<TripLevel> tripsList, int currentMaxKey) {
                _levels = tripsList;
            }

            @Override
            public void onCancel() {

            }
        });

        Model.instance().getAllTripTypesAsynch(new Model.GetAllTripTypesListener() {
            @Override
            public void onComplete(List<TripType> tripsList, int currentMaxKey) {
                _types = tripsList;
            }

            @Override
            public void onCancel() {

            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("מעדכן נתונים...");
        progressDialog.show();
        User.getInstance().setPreferedAreas(customAdapter._preferedAreas);

        Model.instance().signUp(User.getInstance(), new Model.SuccessListener() {
            @Override
            public void onResult(boolean result) {
                if (result) {
                    Model.instance().setConnectedUser(User.getInstance());
                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Context context = getApplicationContext();
                    CharSequence text = "התרחשה שגיאה בהרשמה";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

       /* String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }*/

        return valid;
    }


    class typesAdapter extends BaseAdapter {

        private class ViewHolder {
            CheckedTextView txtView;
        }

        @Override
        public int getCount() {
            return _types.size();
        }

        @Override
        public TripType getItem(int i) {
            return _types.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View convertView, final ViewGroup viewGroup) {
            final ViewHolder holder;
            LayoutInflater inflater = getLayoutInflater();

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.check_list, null);
                holder = new ViewHolder();

                holder.txtView = (CheckedTextView) convertView.findViewById(R.id.simpleCheckedTextView);
                convertView.setTag(holder);
            } else {

                holder = (ViewHolder) convertView.getTag();
            }

            holder.txtView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (holder.txtView.isChecked()) {
                        // set cheek mark drawable and set checked property to false
                        value = "un-Checked";
                        holder.txtView.setCheckMarkDrawable(null);
                        holder.txtView.setChecked(false);
                        User.getInstance().getPreferedTypes().remove(Integer.parseInt(
                                holder.txtView.getTag().toString()));

                    } else {

                        // set cheek mark drawable and set checked property to true
                        value = "Checked";
                        User.getInstance().getPreferedTypes().add(Integer.parseInt(
                                holder.txtView.getTag().toString()));

                        holder.txtView.setCheckMarkDrawable(R.drawable.checked);
                        holder.txtView.setChecked(true);
                    }
                }
            });


            return convertView;
        }
    }

    class levelAdapter extends BaseAdapter {

        private class ViewHolder {
            CheckedTextView txtView;
        }
        @Override
        public int getCount() {
            return _levels.size();
        }

        @Override
        public TripLevel getItem(int i) {
            return _levels.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View convertView, final ViewGroup viewGroup) {
            final ViewHolder holder;
            LayoutInflater inflater = getLayoutInflater();

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.check_list, null);
                holder = new ViewHolder();

                holder.txtView = (CheckedTextView) convertView.findViewById(R.id.simpleCheckedTextView);
                convertView.setTag(holder);
            } else {

                holder = (ViewHolder) convertView.getTag();
            }

            holder.txtView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (holder.txtView.isChecked()) {
                        // set cheek mark drawable and set checked property to false
                        value = "un-Checked";
                        holder.txtView.setCheckMarkDrawable(null);
                        holder.txtView.setChecked(false);
                        User.getInstance().setLevel(1);

                    } else {

                        levelListView.clearChoices();
                        // set cheek mark drawable and set checked property to true
                        value = "Checked";
                        User.getInstance().setLevel(Integer.parseInt(holder.txtView.getTag().toString()));

                        holder.txtView.setCheckMarkDrawable(R.drawable.checked);
                        holder.txtView.setChecked(true);
                    }
                }
            });



            return convertView;
        }
    }
}