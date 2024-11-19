
package com.s22010151.parkspotter_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Distance extends AppCompatActivity implements SensorEventListener {

    // UI elements
    private TextView textView;
    private BottomNavigationView bottomNavigationView;

    // Sensor related variables
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float[] lastAccelValues = new float[3];
    private float[] currentAccelValues = new float[3];
    private float distance = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance);

        // Initialize UI elements
        textView = findViewById(R.id.sensortxt);
        bottomNavigationView = findViewById(R.id.bottomnavigation);

        // Initialize sensor manager and accelerometer
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Set bottom navigation item selected listener
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            // Start different activities based on selected item
            if (itemId == R.id.dashboard) {
                startActivity(new Intent(getApplicationContext(), Dashboard.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (itemId == R.id.history) {
                startActivity(new Intent(getApplicationContext(), Locate.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (itemId == R.id.profile) {
                startActivity(new Intent(getApplicationContext(), UserProfile.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Update accelerometer values and calculate distance
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, currentAccelValues, 0, event.values.length);
            calculateDistance();
        }
    }

    private void calculateDistance() {
        // Calculate delta values and total distance
        float deltaX = currentAccelValues[0] - lastAccelValues[0];
        float deltaY = currentAccelValues[1] - lastAccelValues[1];
        float deltaZ = currentAccelValues[2] - lastAccelValues[2];

        float delta = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
        distance += delta;

        // Update last accelerometer values
        lastAccelValues[0] = currentAccelValues[0];
        lastAccelValues[1] = currentAccelValues[1];
        lastAccelValues[2] = currentAccelValues[2];

        // Update text view with calculated distance
        textView.setText(String.format("Distance: %.2f", distance));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used in this implementation
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register accelerometer sensor listener
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister accelerometer sensor listener to save battery
        sensorManager.unregisterListener(this);
    }
    public void gobackFindaspot(View view) {
        startActivity(new Intent(this, Findaspot.class)); // Start Legal activity
    }
}
