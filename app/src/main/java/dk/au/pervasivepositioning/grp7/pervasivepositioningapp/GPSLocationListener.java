package dk.au.pervasivepositioning.grp7.pervasivepositioningapp;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class GPSLocationListener implements LocationListener {

    private Activity activity;

    public GPSLocationListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onLocationChanged(Location loc) {

        try {
            FileWriter writer = new FileWriter("gps-coordinates.txt");
            writer.append(System.currentTimeMillis() + " ");
            writer.append(loc.getLatitude() + " ");
            writer.append(loc.getLongitude() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Toast.makeText(
                activity,
                "Location changed: Lat: " + loc.getLatitude() + " Lng: "
                        + loc.getLongitude(), Toast.LENGTH_SHORT).show();
        String longitude = "Longitude: " + loc.getLongitude();
        Log.v(TAG, longitude);
        String latitude = "Latitude: " + loc.getLatitude();
        Log.v(TAG, latitude);
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
}
