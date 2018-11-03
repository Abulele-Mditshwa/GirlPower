package com.example.admin.girlpower;

/*
This Fragment will display our Notifications.
 */


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationsFragment extends Fragment {

    public TextView message;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.notifications_fragment, container,false);// sets which xml file to use for this fragment

        message = (TextView)view.findViewById(R.id.textNotify);

    //    String strtext = getArguments().getString("message");

//        message.setText(strtext);

        return view;
    }//end onCreateView.



}//end NotificationFragment
