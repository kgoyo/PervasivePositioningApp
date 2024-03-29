package dk.au.pervasivepositioning.grp7.pervasivepositioningapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnGetLocation = null;
    private EditText etGetTTF = null;
    private EditText etGetMTF = null;
    private EditText etGetS1 = null;
    private EditText etGetS2 = null;
    private EditText etGetM1 = null;
    private EditText etGetM2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetLocation = (Button) findViewById(R.id.button1);
        btnGetLocation.setOnClickListener(this);


        // **** PERIODIC ****
        etGetTTF = (EditText) findViewById(R.id.editText);
        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton);

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            LocationManager locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            LocationListener locationListener = new GPSPeriodicLocationListener(getParent());

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String etText = etGetTTF.getText().toString();
                    int timeBetweenFix;
                    if (etText.matches("")) {
                        timeBetweenFix = 1000;
                    } else {
                        timeBetweenFix = Integer.parseInt(etText) * 1000;
                    }
                    float minDist = 0;

                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    locMan.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER, timeBetweenFix, minDist, locationListener);
                } else {
                    locMan.removeUpdates(locationListener);
                }
            }
        });

        // **** DISTANCE ****

        etGetMTF = (EditText) findViewById(R.id.editTextDist);
        ToggleButton toggleDist = (ToggleButton) findViewById(R.id.toggleButtonDist);

        toggleDist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            LocationManager locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            LocationListener locationListener;

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String etText = etGetMTF.getText().toString();
                    int metersBetweenFix;
                    if (etText.matches("")) {
                        metersBetweenFix = 50;
                    } else {
                        metersBetweenFix = Integer.parseInt(etText);
                    }

                    locationListener = new GPSDistanceLocationListener(getParent(), metersBetweenFix,"gps2");

                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    locMan.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
                } else {
                    locMan.removeUpdates(locationListener);
                }
            }
        });

        // **** SPEED ****

        etGetS1 = (EditText) findViewById(R.id.editTextSpeed1);
        etGetS2 = (EditText) findViewById(R.id.editTextSpeed2);
        ToggleButton toggleSpeed = (ToggleButton) findViewById(R.id.toggleButtonSpeed);

        toggleSpeed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            LocationManager locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            LocationListener locationListener;

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String et1Text = etGetS1.getText().toString();
                    String et2Text = etGetS2.getText().toString();

                    int et1;
                    if (et1Text.matches("")) {
                        et1 = 50;
                    } else {
                        et1 = Integer.parseInt(et1Text);
                    }

                    int et2;
                    if (et2Text.matches("")) {
                        et2 = 2;
                    } else {
                        et2 = Integer.parseInt(et2Text);
                    }

                    locationListener = new GPSDistanceLocationListener(getParent(), et1,"gps3");

                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    locMan.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER, (et1/et2)*1000, 0, locationListener);
                } else {
                    locMan.removeUpdates(locationListener);
                }
            }
        });

        // **** MOVEMENT ****

        etGetM1 = (EditText) findViewById(R.id.editTextMove1);
        etGetM2 = (EditText) findViewById(R.id.editTextMove2);
        ToggleButton toggleMove = (ToggleButton) findViewById(R.id.toggleButtonMove);

        toggleMove.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            LocationManager locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            SensorManager senMan = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            Sensor accelerometer = senMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            LocationListener locationListener;
            AccelerometerSensorListener accListener;

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String et1Text = etGetM1.getText().toString();
                    String et2Text = etGetM2.getText().toString();

                    int et1;
                    if(et1Text.matches("")) {
                        et1 = 50;
                    } else {
                        et1 = Integer.parseInt(et1Text);
                    }

                    int et2;
                    if(et2Text.matches("")) {
                        et2 = 2;
                    } else {
                        et2 = Integer.parseInt(et2Text);
                    }

                    int goalTime = (et1/et2)*1000;

                    locationListener = new GPSMovementLocationListener(getParent(), goalTime, "gps4");
                    accListener = new AccelerometerSensorListener(locMan, locationListener, getApplicationContext(), goalTime);

                    senMan.registerListener(accListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                } else {
                    senMan.unregisterListener(accListener);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        try {
            DateFormat format = new SimpleDateFormat("HH:mm:ss");
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File( sdCard.getAbsolutePath(), "PervPos");
            dir.mkdirs();
            File file = new File(dir, "timestamps.txt");
            FileWriter writer = null;
            writer = new FileWriter(file, true);
            writer.append(format.format(new Date()) + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}