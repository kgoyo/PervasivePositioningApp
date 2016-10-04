package dk.au.pervasivepositioning.grp7.pervasivepositioningapp;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class GPSPeriodicLocationListener implements LocationListener {

    private Activity activity;

    public GPSPeriodicLocationListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onLocationChanged(Location loc) {
        try {
            DateFormat format = new SimpleDateFormat("HH:mm:ss");
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File( sdCard.getAbsolutePath(), "PervPos");
            dir.mkdirs();
            File file = new File(dir, "gps1.txt");
            FileWriter writer = null;
            writer = new FileWriter(file, true);
            writer.append(format.format(new Date()) + " ");        //hh:mm:ss
            writer.append(loc.getLatitude() + " ");
            writer.append(loc.getLongitude() + "\n");
            writer.close();
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
