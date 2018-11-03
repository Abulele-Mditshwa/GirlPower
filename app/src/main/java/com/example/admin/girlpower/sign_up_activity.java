package com.example.admin.girlpower;
/*
This is the class that the user registers
 */



import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import android.support.v7.app.ActionBarDrawerToggle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class sign_up_activity extends AppCompatActivity implements View.OnClickListener{

    public EditText Email_register,Password_register;
    public  Button register_btn,sign_inButton;
    private ProgressBar progressbar;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference; // refers to our firebase database. With this reference we can store our data on firebase.
    private FirebaseAuth.AuthStateListener mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_activity);

        firebaseAuth = FirebaseAuth.getInstance(); // initialises our firebase auth object



        Email_register = (EditText)findViewById(R.id.email); //XML Reference
        Password_register = (EditText)findViewById(R.id.password);
        progressbar = (ProgressBar) findViewById(R.id.progressBar);


        sign_inButton = (Button)findViewById(R.id.log_in_btn);
        register_btn = (Button)findViewById(R.id.register_btn);


        sign_inButton.setOnClickListener(this); // listener
        register_btn.setOnClickListener(this);


    }//end onCreate method



    //this method creates a user
    public void RegisterUser(){

         String user_email,user_Password; // what the user enters
        user_email = Email_register.getText().toString().trim(); // converts to string
        user_Password = Password_register.getText().toString().trim();



        //checks if the user entered something in the EditTexts
         if(TextUtils.isEmpty(user_email)){
         Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
         return;// stops execution
            }//end else if

        if(TextUtils.isEmpty(user_Password) ){
            Toast.makeText(this, "Please enter your password",Toast.LENGTH_SHORT).show(); // displays the error to the user
            return; //stops execution
        }//end if statement.

        // if the password is short
        if(user_email.length() < 6){
            Toast.makeText(sign_up_activity.this, " Password Length too short, must be 6 characters or more !", Toast.LENGTH_SHORT).show();
            return;
        }//end


        //pass the email to the next Activity
      //  Intent intent = new Intent(sign_up_activity.this, User_info.class);
        //intent.putExtra(Intent.EXTRA_TEXT,user_email);
        //startActivity(intent);

        progressbar.setVisibility(View.VISIBLE); // show progress bar
        firebaseAuth.createUserWithEmailAndPassword(user_email,user_Password).addOnCompleteListener(
                this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(sign_up_activity.this, "Registered successfully",Toast.LENGTH_SHORT).show();
                            // this takes the user back to the main page.
                            // going to change this later. NB Abulele please read this.
                            // takes the user to the activity where he/she must enter they details.
                            Intent intent = new Intent(sign_up_activity.this, User_inf.class);
                            finish(); // closes this current activity.
                            startActivity(intent);
                        }//end if

                        // tells the user the email exists already.
                        else if (!task.isSuccessful() && (task.getException() instanceof FirebaseAuthUserCollisionException)) {
                          //  if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(sign_up_activity.this, "User with this email already exist.", Toast.LENGTH_SHORT).show();
                                return;
                        }//end if

                         // tells the user to enter the correct credentials.
                        else if(!task.isSuccessful() && (task.getException() instanceof FirebaseAuthInvalidCredentialsException) ){
                            Toast.makeText(sign_up_activity.this, "Invalid Credentials Entered.", Toast.LENGTH_SHORT).show();

                            return;
                         }//end if

                        else{

                            Toast.makeText(sign_up_activity.this, "Failed to Login", Toast.LENGTH_SHORT).show();
                        }


                    }//end onComplete method.

                }); //end anonymous inner class.

        progressbar.setVisibility(View.GONE); // hides progress bar.
    }//end RegisterUser


 //This handles events
    //abstract method
    @Override
    public void onClick(View view) {


        //when the user clicks the Register Button
        if(view  == register_btn){
            RegisterUser(); //method call
        }//end if

        if(view == sign_inButton){
            Intent intent = new Intent(sign_up_activity.this, MainActivity.class);
            finish(); // closes this current activity.
            startActivity(intent);
        }//end if

    }//end onClick Method



}//end class sign_up_activity
