package com.example.admin.girlpower;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    public TextView NavName, NavEmail;

    private String TAG = "User Logged in";

    // All the firebase stuff to retrieve the user stuff
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String User_id; // to define each user.

    private View navHeader; // to access the nav header

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseUser = firebaseAuth.getCurrentUser();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseUser != null){


                    Log.d("User loggin in", "User " +firebaseUser.getEmail()+ " signed in");
                    //Toast.makeText(Main2Activity.this,"User "+firebaseUser.getEmail()+" signed in ", Toast.LENGTH_SHORT).show();
                }//end if.

                else{
                    Toast.makeText(Main2Activity.this, "User logged out",Toast.LENGTH_SHORT).show();
                }//end else.

            }//end onAuthStateChanged.
        };


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        My_Activity my_activity = new My_Activity();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Navigation View header

        navHeader = navigationView.getHeaderView(0);

        NavEmail = (TextView)navHeader.findViewById(R.id.Nav_email);
        NavName = (TextView)navHeader.findViewById(R.id.Nav_name);




        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();

           NavEmail.setText(email);
            NavName.setText(name);

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            String uid = user.getUid();
        }//end if


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users"); // ref


        User_id = firebaseUser.getUid();
    }//end onCreate.

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }//end onBackPressed



    //creates the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }//end OnCreateOptionsMenu


    //listener for
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

          Fragment fragment = null;

        int id = item.getItemId();

        if (id == R.id.home) {

           fragment = new My_Activity();// calls this Fragment.
            // Handle the camera action
        }//end if

        else if (id == R.id.notifications) {

            fragment = new NotificationsFragment();// calls the Notifications Fragment

        } //end else if
        else if (id == R.id.options) {
            fragment = new Options_in_Emergency(); // calls the Emergency fragment

        }//end else if
        else if (id == R.id.settings) {
            fragment = new Settings(); // calls the settings Fragment.
        }//end else if

       if(fragment != null ){

            //this just replace each fragment page.
        FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.screen_area,fragment); // change the
            fragmentTransaction.commit(); // commit to the changes

        }//end if

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }//end onNavigationItemSelected


    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }//end onStart.

    @Override
    protected void onStop() {
        super.onStop();

        if(mAuthListener != null ){
            firebaseAuth.addAuthStateListener(mAuthListener);
        }//end if

    }//end onStop.

}//end Main2Activity.


