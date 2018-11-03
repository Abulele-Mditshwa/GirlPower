package com.example.admin.girlpower;
/*
This is the class that the user logs in to
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth; // this class is for authenticating our users.


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    public static EditText email,password;// defining the global variables
    private Button Btnlogin,Forgotbtn,Signbtn; // button in main activity xml
    private FirebaseAuth firebaseAuth;
    private ProgressBar bar; // shows the user that some action is happening.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance(); // initialises our firebase auth object

        //checks if the user is already logged in.
        if(firebaseAuth.getCurrentUser() != null){
        //    Intent intent = new Intent(MainActivity.this, Main2Activity.class); // go to the main page
            finish(); // closes this current activity and opens the new one
           // startActivity(intent);
        }//end if


        // these are the XML reference.
        email = (EditText)findViewById(R.id.email); //XML reference
        password = (EditText)findViewById(R.id.password);
        Btnlogin = (Button)findViewById(R.id.btn_login);
        Forgotbtn = (Button)findViewById(R.id.btn_reset_password);
        Signbtn = (Button)findViewById(R.id.btn_signup);
        bar = (ProgressBar)findViewById(R.id.progressBar);



        //listeners
        Btnlogin.setOnClickListener(this);// goes to the onClick Method
        Forgotbtn.setOnClickListener(this);// goes to the onClick Method
        Signbtn.setOnClickListener(this);// goes to the onClick Method



    }//end onCreate




    // when the user logins in
    public void UserLogin(){
        final String Email,Password;

        Email = email.getText().toString();//converts to string
        Password = password.getText().toString();

        //checks whether the user entered something in the EditTextFields
        if(TextUtils.isEmpty(Email)|| TextUtils.isEmpty(Password)){

            Toast.makeText(MainActivity.this, "Email or Password Field is Missing !",Toast.LENGTH_LONG).show(); // tells the user the error

            return; // stops execution.
        }//end if

        bar.setVisibility(View.VISIBLE); // we can the progress bar. Some thing happening.

        firebaseAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(
                this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            Intent intent2 = new Intent(MainActivity.this, Main2Activity.class); // go to the main page

                            //===========================================================================================
                            //passes the user the email.


                            //============================================================================================
                            finish(); // closes this current activity and opens the new one
                            startActivity(intent2);


                        }//end if
                        else{
                            Toast.makeText(MainActivity.this, "Failed to login", Toast.LENGTH_SHORT).show();
                        }//end else

                        bar.setVisibility(View.GONE); // removes the progressBar
                    }//end onComplete method
                }); //end Anonymous inner class

    }//end UserLogin method









    // must include this method because of the abstract.
    @Override
    public void onClick(View view) {
        // if the user click the button
        if( view == Btnlogin){
           UserLogin();// method call

        }//end if statement

        if(view == Forgotbtn){
            Intent intent = new Intent(MainActivity.this, ForgetPassword.class); // go to the main page
            finish(); // closes this current activity and opens the new one
            startActivity(intent);

        }//end if

        if(view == Signbtn){
            Intent intent = new Intent(MainActivity.this, sign_up_activity.class); // go to the main page
            finish(); // closes this current activity and opens the new one
            startActivity(intent);

        }//end if


    }//end onClick abstract method






}// end MainActivity class.
