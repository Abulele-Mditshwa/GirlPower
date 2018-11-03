package com.example.admin.girlpower;

/*
User is able to make emergency phone calls on this calls.
 */

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class emergencyOption extends AppCompatActivity {

    private Button call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_option);


        call = (Button)findViewById(R.id.callBtn);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:911"));
                    startActivity(intent);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                else {
                    Log.e("Dial Error ", "Can't resolve app for ACTION_DIAL Intent.");
                }
            }
        });


    }//end onCreate



}//end emergencyOption class.
