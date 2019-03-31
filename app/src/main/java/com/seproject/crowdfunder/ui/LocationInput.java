package com.seproject.crowdfunder.ui;

import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.seproject.crowdfunder.R;
import com.seproject.crowdfunder.Utils.GPSTracker;

public class LocationInput extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    GPSTracker gpsTracker;
    Double lat,longi;
    Location yourLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_input);


        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }


        gpsTracker = new GPSTracker(this);
        yourLocation = new Location("A");
        yourLocation.setLatitude(gpsTracker.getLatitude());
        yourLocation.setLongitude(gpsTracker.getLongitude());

    }
}
