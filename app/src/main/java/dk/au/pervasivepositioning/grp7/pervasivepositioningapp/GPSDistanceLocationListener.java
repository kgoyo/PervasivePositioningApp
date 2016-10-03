package dk.au.pervasivepositioning.grp7.pervasivepositioningapp;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GPSDistanceLocationListener implements LocationListener {

    private Activity activity;
    private int distance;
    private Location previousLocation;
    private String fileName;

    public GPSDistanceLocationListener(Activity activity, int distance, String fileName) {
        this.activity = activity;
        this.distance = distance;
        this.fileName = fileName;
    }

    @Override
    public void onLocationChanged(Location loc) {
        try {
            if (previousLocation == null || loc.distanceTo(previousLocation) >= distance) {
                previousLocation = loc;

                DateFormat format = new SimpleDateFormat("HH:mm:ss");
                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File( sdCard.getAbsolutePath(), "PervPos");
                dir.mkdirs();
                File file = new File(dir, fileName + ".txt");
                FileWriter writer = null;
                writer = new FileWriter(file, true);
                writer.append(format.format(new Date()) + " ");        //hh:mm:ss
                writer.append(loc.getLatitude() + " ");
                writer.append(loc.getLongitude() + "\n");
                writer.close();
            } else {

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
}
