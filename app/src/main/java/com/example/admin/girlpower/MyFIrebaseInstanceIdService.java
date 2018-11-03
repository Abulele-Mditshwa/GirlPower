package com.example.admin.girlpower;

/*
this class allows us to retrieve firebase tokens so that we can individual firebase messsages.
 */

import android.content.pm.ApplicationInfo;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFIrebaseInstanceIdService extends FirebaseInstanceIdService {

    private String REG_TOKEN = "Token :";

    // generates a new token each time
    @Override
    public void onTokenRefresh() {

        String recent_token = FirebaseInstanceId.getInstance().getToken();
        Log.d(REG_TOKEN,"here is toke" + recent_token); // prints out the token number.
    }






}//end classs
