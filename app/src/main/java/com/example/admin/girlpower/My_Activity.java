package com.example.admin.girlpower;
/*
This Fragment will check if the user has the correct google maps versions and if so take him to the maps activt
 */


import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;



import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Location;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;


public class My_Activity extends Fragment {

    private static String TAG = "My activity";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private ImageView Location,Profile;
    private FloatingActionButton panicButton;
    private TextView test;
    private ImageButton callEmergency;


    // firebase things to access uniques user data
    //===================================
    private FirebaseUser user; //
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference; // get reference of the database.
    private FirebaseAuth firebaseAuth; //gets instance of the user authorisation.
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseUser firebaseUser;

    private String UserID;
    private String TAG2 = "Database";

    final int SEND_SMS_PERMISSION_REQUEST_CODE=1;

    private String number1,number2,number3; // emergency numbers that will be taken from the database.

    private String nameOfUser,message;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


    }//end onCreate

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.my_activity, container,false);

        Location = (ImageView)view.findViewById(R.id.location);
        Profile = (ImageView)view.findViewById(R.id.My_Profile);
        panicButton = (FloatingActionButton)view.findViewById(R.id.Panic_button);

        callEmergency = (ImageButton) view.findViewById(R.id.Report);


        if (checkPermission(Manifest.permission.SEND_SMS)) {
            panicButton.setEnabled(true);
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.SEND_SMS},
                    SEND_SMS_PERMISSION_REQUEST_CODE);
        }



         Location.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 //System.out.println("You pressed ");
                 Intent intent = new Intent(getActivity(),MapsActivity.class);
                 startActivity(intent);
             }
         });


        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),User_inf.class);
                startActivity(intent);
            }
        });



        // go to the other class.
        callEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),emergencyOption.class);  // opens this class.
                startActivity(intent);
            }
        });

        
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        firebaseUser = firebaseAuth.getCurrentUser();

        databaseReference = firebaseDatabase.getReference("Users"); // ref

        UserID = firebaseUser.getUid();



        // this takes the numbers from the database and sends it to the numbers the user inserted.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // read entire database.
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    try {

                        // the code below extracts the data from the firebase.
                          number1 = ds.child("Emergency_1").getValue().toString();
                          number2 = ds.child("Emergency_2").getValue().toString();
                          number3 = ds.child("Emergency_3").getValue().toString();
                          nameOfUser = ds.child("Name").getValue().toString();
  //================= end getting data from the firebase =======================================================================
                          String locationPassed = " ";
                          UserLocation obj  = new UserLocation();
                          String mylocation = obj.passLocation(locationPassed);
                          message = "Hello this is " + nameOfUser + " Please help me I'm in an emergency ";
                    }//end try

                    catch (NullPointerException e){
                        e.printStackTrace();
                        System.out.print("This threw a null pointer exception");
                    }//end
                }//end for loop.
            }//end onDataChange.



            //in case you can't get the data
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
          //  Toast.makeText(getActivity(),"Couldn't get numbers from database",Toast.LENGTH_SHORT).show();

            }//end onCancelled.sendMessage(number1,number2,number3,message); //method call
        }); // end anonymous inner class.



        // send the message
        panicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(); // method call.
            }
        });// end anonymouss inner class
        return view;
    }//end onCreateView



//=======================================================================================
    //check for the users permission first.
    private boolean checkPermission(String permission) {
        int permissionCheck =
                ContextCompat.checkSelfPermission(getActivity(),permission);
        return (permissionCheck ==
                PackageManager.PERMISSION_GRANTED);
    }
    //===================================================================================



    //===================================================================================
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case SEND_SMS_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    panicButton.setEnabled(true);
                }
                return;
            }
        }//end switch


    }//end onRequestPermissionResult.
//================================================================================================






//================================================================================================
    public void sendMessage(){

        if (checkPermission(Manifest.permission.SEND_SMS)) {
            SmsManager smsManager1 = SmsManager.getDefault();
            SmsManager smsManager2 = SmsManager.getDefault(); // for the second number
            SmsManager smsManager3 = SmsManager.getDefault();

            //sends the message to all the numbers.
            smsManager1.sendTextMessage(number1, null, message,null, null);
            smsManager2.sendTextMessage(number2, null, message,null, null);
            smsManager3.sendTextMessage(number3, null, message,null, null);

           // Toast.makeText(getActivity(), "Messages Sent ",Toast.LENGTH_SHORT).show();

        }//end if

        else {
            // Toast.makeText(getActivity(), "No Permission",Toast.LENGTH_SHORT).show();
        }//end else


    }//end sendMessage
//===============================================================================================================================












}//end My_activity.


