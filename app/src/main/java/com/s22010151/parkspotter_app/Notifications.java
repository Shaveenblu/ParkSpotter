
package com.s22010151.parkspotter_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Notifications extends AppCompatActivity {

    // UI elements
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        // Initialize bottom navigation view
        bottomNavigationView = findViewById(R.id.bottomnavigation);

        // Set item selected listener for bottom navigation view
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            // Handle different menu items
            if (itemId == R.id.dashboard) {
                startActivity(new Intent(getApplicationContext(), Dashboard.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish(); // Finish current activity
                return true;
            } else if (itemId == R.id.history) {
                startActivity(new Intent(getApplicationContext(), Locate.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish(); // Finish current activity
                return true;
            } else if (itemId == R.id.profile) {
                startActivity(new Intent(getApplicationContext(), UserProfile.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish(); // Finish current activity
                return true;
            }

            return false; // Return false if no item is selected
        });
    }
    public void gobackProfilefromnotification(View view) {
        startActivity(new Intent(this, UserProfile.class)); // Start Legal activity
    }
}


