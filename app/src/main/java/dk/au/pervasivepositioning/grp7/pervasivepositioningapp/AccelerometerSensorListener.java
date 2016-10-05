package dk.au.pervasivepositioning.grp7.pervasivepositioningapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by bross on 10/3/16.
 */

public class AccelerometerSensorListener implements SensorEventListener {

    private static final int THRESHOLD = 10;
    private static final int TIME2STANDINMILLIS = 700;
    private LocationManager manager;
    private LocationListener listener;
    private Context context;
    private int goalTime;
    private long last;
    private long time;


    public AccelerometerSensorListener(LocationManager locMan, LocationListener locationListener, Context context, int goalTime) {
        this.manager = locMan;
        this.listener = locationListener;
        this.context = context;
        this.goalTime = goalTime;
        last = 0;
        time = 0;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        double change = Math.sqrt(Math.pow(values[0], 2) + Math.pow(values[1], 2) + Math.pow(values[2], 2));
        if (change > THRESHOLD) {
            
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            long current = System.currentTimeMillis();
            long diff = current - last;
            if ( diff < TIME2STANDINMILLIS ){
                time += diff;

            }
            Log.d("TestMoveSM", "Step: " + time);

            if (time >= goalTime) {
                time = time % goalTime;
                Log.d("TestMoveSM", "GPS should be recorded");
                manager.requestSingleUpdate(LocationManager.GPS_PROVIDER, listener, null);
            }

            last = current;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
