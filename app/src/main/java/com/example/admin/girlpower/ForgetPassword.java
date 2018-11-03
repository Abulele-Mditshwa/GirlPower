package com.example.admin.girlpower;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class ForgetPassword extends AppCompatActivity implements View.OnClickListener {


    private EditText email;
    private Button Reset_btn,Back_btn;
    private ProgressBar bar; // displays when something is loading.
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        firebaseAuth = FirebaseAuth.getInstance();

        // XML reference
        email = (EditText)findViewById(R.id.email);
        Reset_btn = (Button)findViewById(R.id.btn_reset_password);
        Back_btn = (Button)findViewById(R.id.btn_back);
        bar = (ProgressBar)findViewById(R.id.progressBar);

        // my listeners for the buttons
        Reset_btn.setOnClickListener(this);
        Back_btn.setOnClickListener(this);

    }//end onCreate method




    // this method sends an email to the user with a link to reset the password.
    public void sendEmail(){
        final String user_email = email.getText().toString().trim();

        // if the
        if(TextUtils.isEmpty(user_email)){
            Toast.makeText(this,"Enter your email !",Toast.LENGTH_SHORT).show();

            return; //stops the execution
        }//end if

        bar.setVisibility(View.VISIBLE); // when the user clicks the reset button the progress bar will be visible.

        firebaseAuth.sendPasswordResetEmail(user_email).addOnCompleteListener(
                this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // when it is succesfull

                        if(task.isSuccessful()){
                            Toast.makeText(ForgetPassword.this,"We sent you instructions to reset your password to your email",Toast.LENGTH_LONG).show();
                            // succesfully sent an email.
                        }//end if
                        // if it fails

                        // tells the user to enter the correct credentials.
                        if(!task.isSuccessful() && (task.getException() instanceof FirebaseAuthInvalidCredentialsException) ){
                            Toast.makeText(ForgetPassword.this, "Invalid Credentials Entered.", Toast.LENGTH_SHORT).show();
                            return;
                        }//end if

                        // if the user enters an email that doesn't exist or isn't registered in the app
                        if(!task.isSuccessful() && (task.getException() instanceof FirebaseAuthInvalidUserException) ){
                            Toast.makeText(ForgetPassword.this, "There's no record of this corresponding email " + user_email + " !.Please register first.", Toast.LENGTH_LONG).show();
                            return;
                        }//end if

                      //  else{
                        //    Toast.makeText(ForgetPassword.this,"Failed to reset password " + task.getException(),Toast.LENGTH_SHORT).show(); // failed to send an email
                        //}//end else


                    }//end on OnComplete

                });// end anonymous inner class

        bar.setVisibility(View.GONE); // progress bar disapears.

    }//end sendEmail



    // handles the events
    @Override
    public void onClick(View v) {
        //handles the action when the user presses the Reset button
        if(v == Reset_btn){
           sendEmail(); // method call
            }//end if

        // handles when the user presses the back button
        if(v == Back_btn){
            Intent intent = new Intent(ForgetPassword.this, MainActivity.class);
            finish();
            startActivity(intent);
        }//end if


    }//end onClick



}//end ForgetPassword class
