package com.example.admin.girlpower;
/*
This class saves the user info into the firebase database.
 */

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User_inf extends AppCompatActivity implements View.OnClickListener{
    public EditText name,surname,email,phone,emergency_1,emergency_2,emergency_3;
    private Button Save_data;

    private FirebaseAuth.AuthStateListener mAuthState;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;  // store my data
    private DatabaseReference databaseReference; // for referring to the database.

    private String TAG = "addFirebase DATABASE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_inf);

        firebaseAuth = FirebaseAuth.getInstance();// gets instance of the user.

        // if the user is not logged in
         if(firebaseAuth.getCurrentUser() == null){
           Intent intent = new Intent(this, sign_up_activity.class);
         finish();
        startActivity(intent);
        }//end if




        //XML reference.
        name = (EditText)findViewById(R.id.Name);
        surname = (EditText)findViewById(R.id.Surname);
        email = (EditText)findViewById(R.id.user_email);
        phone = (EditText)findViewById(R.id.User_phone);

        //this will be stored in a seperate JSON object in the database.
        emergency_1 = (EditText)findViewById(R.id.Emergency_1);
        emergency_2 = (EditText)findViewById(R.id.Emergency_2);
        emergency_3 = (EditText)findViewById(R.id.Emergency_3);




        firebaseDatabase = FirebaseDatabase.getInstance(); // gets the instance of the database.


        //allows us to write to a particular location in the database.
        databaseReference  = firebaseDatabase.getReference();

        //sets the email EditText to what the user entered in the previous activity.
        //  if(getIntent()!=null && getIntent().hasExtra(Intent.EXTRA_TEXT)) {
        //         email.setText(getIntent().getStringExtra(Intent.EXTRA_TEXT));
        //      }//end if.



        Save_data = (Button)findViewById(R.id.save_btn);
        Save_data.setOnClickListener(this); //listener



    }//end onCreate


    // this method will send the data to the database.
    public void sendData(){

        //converts to string
        String Name = name.getText().toString().trim();
        String Surname = surname.getText().toString().trim();
        String Email = email.getText().toString().trim();
        String Phone = phone.getText().toString().trim();

        //emergency numbers
        String emergency1 = emergency_1.getText().toString().trim();
        String emergency2 = emergency_2.getText().toString().trim();
        String emergency3 = emergency_3.getText().toString().trim();


        //if the user didn't enter they name.
        if(TextUtils.isEmpty(Name)){
            Toast.makeText(User_inf.this, "Please enter your Name", Toast.LENGTH_SHORT).show();
            return;// stops execution
        }//end else if

        // if the user didn't enter the Surname.
        if(TextUtils.isEmpty(Surname) ){
            Toast.makeText(User_inf.this, "Please enter your Surname",Toast.LENGTH_SHORT).show(); // displays the error to the user
            return; //stops execution
        }//end if statement.

        // if the user didn't enter the email.
        if(TextUtils.isEmpty(Email) ){
            Toast.makeText(User_inf.this, "Please enter your Email",Toast.LENGTH_SHORT).show(); // displays the error to the user
            return; //stops execution
        }//end if statement.

        // if the phone number.
        if(Phone.length()  < 10){
            Toast.makeText(User_inf.this, " Phone number is too short, must be 10 characters or more !", Toast.LENGTH_SHORT).show();
            return;
        }//end

        else if (TextUtils.isEmpty(emergency1) || TextUtils.isEmpty(emergency2) || TextUtils.isEmpty(emergency3)){
            Toast.makeText(User_inf.this, "One of the emergency numbers is empty",Toast.LENGTH_SHORT).show();
            return;
        }//end if

        else if (emergency1.length() < 10 || emergency2.length() < 10 || emergency3.length() < 10){
            Toast.makeText(User_inf.this,"All emergency numbers must be equal to 10 digits",Toast.LENGTH_SHORT).show();
            return;
        }



/*
        Intent intentName = new Intent(this, Main2Activity.class);
        Intent intentSurname = new Intent(User_inf.this,Main2Activity.class);
        intentName.putExtra(Intent.EXTRA_TEXT,Name); // sends name
        intentSurname.putExtra(Intent.EXTRA_TEXT,Surname); // sends Surname.
        startActivity(intentName);
        startActivity(intentSurname);
        */


        // passes the data
        UserInformation userInformation = new UserInformation(Name,Surname,Email,Phone,emergency1,emergency2,emergency3);

        FirebaseUser user = firebaseAuth.getCurrentUser();// gets the current user. Each user  will be able to store unique data.
        String userID  = user.getUid();
        databaseReference.child("Users").child(userID).setValue(userInformation);
        //databaseReference.child("Users").push().setValue(userInformation);
        Toast.makeText(User_inf.this, "This user's data " + Name  + " is saved", Toast.LENGTH_SHORT).show();

          Intent intent = new Intent(User_inf.this, Main2Activity.class); // sends the user to the ProfileActivity
           finish(); // closes this current activity.
         startActivity(intent); // opens that activity.
    }//end sendData method.



    //handles the events.
    @Override
    public void onClick(View view) {

        if(view == Save_data){
            sendData(); // method call.
        }//end if

    }//end onClick method




}//end User_info class
