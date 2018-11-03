package com.example.admin.girlpower;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    public TextView user_email;
    private Button button;
    private FirebaseAuth firebaseAuth; // to authenticate our users

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        firebaseAuth = FirebaseAuth.getInstance();






        FirebaseUser user  =  firebaseAuth.getCurrentUser(); // gets the current user for us.

        user_email = (TextView)findViewById(R.id.welcomeTextView); //XML reference
        button = (Button)findViewById(R.id.Log_out_button);
        user_email.setText("Welcome \n" + user.getEmail());

        String email = user_email.toString().trim();



        //passes the user the email.
        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra(Intent.EXTRA_TEXT,email);
        startActivity(intent);



        button.setOnClickListener(this); // listener
    }//end onCreate Main2








    @Override
    public void onClick(View v) {
        //when the user presses the log out button.
        // this basically signs the user out and takes him/her back to the login page.
        if(v == button ){
            firebaseAuth.signOut(); // signs the user out of the app
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            finish();
            startActivity(intent);
        }//end if

    }//end onClick method


}//end class ProfileActivity.
