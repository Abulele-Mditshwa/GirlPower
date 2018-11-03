package com.example.admin.girlpower;

/*]
gets the user location thats going to be used in the sms.
 */


import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;



public class UserLocation extends AppCompatActivity {

    double lat,lng;

    public String user_Location;

    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            lat = location.getLongitude();
            lng = location.getLatitude();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try{

            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            Criteria criteria = new Criteria();
            String bestProvider = locationManager.getBestProvider(criteria, true);
            Location location = locationManager.getLastKnownLocation(bestProvider);

            if (location == null) {
                Toast.makeText(getApplicationContext(), "GPS signal not found",
                        Toast.LENGTH_SHORT).show();
            }
            if (location != null) {
                Log.e("locatin", "location--" + location);

                Log.e("latitude at beginning",
                        "@@@@@@@@@@@@@@@" + location.getLatitude());
                onLocationChanged(location);
            }

        }catch (SecurityException e){
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }


    }//end onCreate method.


    public void onLocationChanged(Location location) {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        lat= location.getLatitude();
        lng = location.getLongitude();

        Log.e("latitude", "latitude--" + lat);

        try {
            Log.e("latitude", "inside latitude--" + lng);
            addresses = geocoder.getFromLocation(lat, lng, 1);


            if (addresses != null && addresses.size() > 0) {
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();


                user_Location = "\n\nhere is my location" +address+ ", " +postalCode+ ", "+country;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }//end catch



    }//end class UserLocation.

    public String passLocation(String mylocation){
        mylocation = user_Location;
        return mylocation;
    }







}//end UserLocation class
