/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.campin.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.campin.DB.Model;
import com.campin.Fragments.CardContentFragment;
import com.campin.Fragments.ListContentFragment;
import com.campin.Fragments.TileContentFragment;
import com.campin.R;
import com.campin.Utils.Area;
import com.campin.Utils.CircleTransform;
import com.campin.Utils.Trip;
import com.campin.Utils.TripLevel;
import com.campin.Utils.TripType;
import com.campin.Utils.User;
import com.facebook.login.LoginManager;

import java.util.ArrayList;
import java.util.List;


/**
 * Provides UI for the main screen.
 */
public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private static final String urlProfileImg =
            "https://graph.facebook.com/";
    private static final String profileEnd =
            "/picture?type=large";

    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtMail;
    private static User _usr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Setting ViewPager for each Tabs
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        // Create Navigation drawer and inlfate layout
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        navHeader = navigationView.getHeaderView(0);

        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtMail = (TextView) navHeader.findViewById(R.id.website);


        User.isSignUp = true;
        // Adding menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            VectorDrawableCompat indicator
                    = VectorDrawableCompat.create(getResources(), R.drawable.ic_menu, getTheme());
            indicator.setTint(ResourcesCompat.getColor(getResources(),R.color.white,getTheme()));
            supportActionBar.setHomeAsUpIndicator(indicator);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        _usr = User.getInstance();

        if (_usr.getUserId() == null)
        {
            _usr = (User) getIntent().getSerializableExtra("user");
        }

        loadNavHeader();
        loadNavigationContent(navigationView);

        // Set behavior of Navigation drawer
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    // This method will trigger on item Click of navigation menu
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Set item in checked state
                        menuItem.setChecked(true);

                        final Intent i = new Intent(MainActivity.this, SignupActivity.class);
                        startActivity(i);

                        // Closing drawer on item click
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
        // Adding Floating Action Button to bottom right of main view
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(v.getContext(), CreateTripActivity.class);
                startActivity(intent);
            }
        });

        loadData();
    }

    private void loadData(){
        Model.instance().getAllTripAsynch(new Model.GetAllTripsListener() {
            @Override
            public void onComplete(List<Trip> tripsList, int currentMaxKey) {
            }

            @Override
            public void onCancel() {
                // Display message
                // TODO: toast
//                Toast.makeText(getActivity().getApplicationContext(), getString(R.string.errorOccure),
//                        Toast.LENGTH_SHORT).show();
            }
        });

        Model.instance().getAllAreaAsynch(new Model.GetAllAreaListener() {
            @Override
            public void onComplete(List<Area> areaList, int currentMaxKey) {
                int a = 2;
            }

            @Override
            public void onCancel() {

            }
        });

        Model.instance().getAllTripLevelsAsynch(new Model.GetAllTripLevelsListener() {
            @Override
            public void onComplete(List<TripLevel> tripsList, int currentMaxKey) {

            }

            @Override
            public void onCancel() {

            }
        });

        Model.instance().getAllTripTypesAsynch(new Model.GetAllTripTypesListener() {
            @Override
            public void onComplete(List<TripType> tripsList, int currentMaxKey) {

            }

            @Override
            public void onCancel() {

            }
        });
    }

    private void loadNavigationContent(NavigationView nav) {
        // get menu from navigationView
        Menu menu = nav.getMenu();

        MenuItem nav_area = menu.findItem(R.id.prof_location);
        nav_area.setTitle(_usr.getLocation());

        MenuItem nav_age = menu.findItem(R.id.prof_age);
        nav_age.setTitle(_usr.getBirthday());

        MenuItem nav_favorite_area = menu.findItem(R.id.prof_fav_area);

        ArrayList<String>    prefferedAreas = _usr.getPreferedAreas();

        String prefered = "";
        int count = prefferedAreas.size();
        for (String area : prefferedAreas)
        {
            prefered += area;

            if (count != 1)
            {
               prefered += ", " ;
            }
            count --;
        }
        nav_favorite_area.setTitle(prefered);

        MenuItem nav_car = menu.findItem(R.id.prof_car);
        nav_car.setTitle("כן");


    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new CardContentFragment(), "טיולים לבחירה");
        //adapter.addFragment(new ListContentFragment(), "טיולים בתכנון");
        adapter.addFragment(new TileContentFragment(), "טיולים בתכנון");
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(Gravity.RIGHT);
        }
        return super.onOptionsItemSelected(item);
    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private void loadNavHeader() {
        // name, website
        // TODO: getting the user data from db.
        txtName.setText(_usr.getFullName());
        txtMail.setText(_usr.getEmail());

        // loading header background image
        Glide.with(this).load(_usr.getUrlCover())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

        // Loading profile image
        Glide.with(this).load(urlProfileImg + _usr.getUserId() + profileEnd)
                .crossFade()
                .thumbnail(1f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);

    }

    /**
     * Back button listener.
     * Will close the application if the back button pressed twice.
     */

    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            LoginManager.getInstance().logOut();
            moveTaskToBack(true); // finish activity
        } else {
            Toast.makeText(this, "לחץ שוב כדי לצאת",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }
}
