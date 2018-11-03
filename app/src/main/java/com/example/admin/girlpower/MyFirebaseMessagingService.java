package com.example.admin.girlpower;

/*
Just recieves the messages on the background
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private Color color;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);



        // if the user recieves the message.
        if(remoteMessage.getData().size() > 0){


            String pushNotification = remoteMessage.getData().get("message");

            Bundle bundle = new Bundle(); // mapping String for parcelable values.

            bundle.putString("message",pushNotification);

            NotificationsFragment passData = new NotificationsFragment();

            passData.setArguments(bundle);

           // passData.setArguments(bundle); // pass the data to the fragment.


        }//end if



        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,intent,PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notifcationBuilder = new NotificationCompat.Builder(this);


        Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); // makes a sound when the message is recieved.



     //   notifcationBuilder.setContentTitle("FCM Notification");
        notifcationBuilder.setContentText(remoteMessage.getNotification().getBody()); // sets our message

        notifcationBuilder.setAutoCancel(true);
        notifcationBuilder.setContentTitle("Girl Power");
        notifcationBuilder.setSmallIcon(R.drawable.femalepower); // icon that will displayed with the message.
        notifcationBuilder.setContentIntent(pendingIntent);
        notifcationBuilder.setSound(notificationSoundUri); // alerts the user when the message arrrives.

        notifcationBuilder.setLights(Color.BLUE, 500, 500);
        notifcationBuilder.setVibrate(new long[]{250,500,250,500,250,500}); // vibrates this long

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notifcationBuilder.build()); // displays the messsage

    }//end onMessageRecieved






}//end class
