
package com.s22010151.parkspotter_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Locate extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate);

        // Initialize and set up bottom navigation view
        bottomNavigationView = findViewById(R.id.bottomnavigation);
        bottomNavigationView.setSelectedItemId(R.id.history);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            // Handle bottom navigation item selection
            if (itemId == R.id.dashboard) {
                startActivity(new Intent(getApplicationContext(), Dashboard.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;

            } else if (itemId == R.id.history) {
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
    public void goBackDashboardfromvehicle(View view) {
        startActivity(new Intent(this, Dashboard.class)); // Start Legal activity
    }
    public void Location(View view) {
        startActivity(new Intent(this, MapActivity.class)); // Start Legal activity
    }

    public void Sensor(View view) {
        startActivity(new Intent(this, Dashboard.class)); // Start Legal activity
    }
    public void Distancetwoplaces(View view) {
        startActivity(new Intent(this, Locatetwoplaces.class)); // Start Legal activity
    }


}