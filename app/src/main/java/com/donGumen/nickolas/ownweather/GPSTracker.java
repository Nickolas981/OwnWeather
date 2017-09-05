package com.donGumen.nickolas.ownweather;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

/**
 * Created by Nickolas on 01.09.2017.
 */

public class GPSTracker implements LocationListener {
    Context c;

    public GPSTracker(Context c) {
        this.c = c;
    }

    public Location getLocation() {
        LocationManager locationManager = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
        boolean isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (isEnabled) {
            if (ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.activity,new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                Toast.makeText(c, "GPS is not granted", Toast.LENGTH_SHORT).show();
                return getLocation();
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 10, this);
            return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } else {
            Toast.makeText(c, "GPS is not Enabled", Toast.LENGTH_SHORT).show();
            return null;
        }
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
