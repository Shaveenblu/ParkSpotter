//UserProfile
package com.s22010151.parkspotter_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserProfile extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Initialize the bottom navigation view
        bottomNavigationView = findViewById(R.id.bottomnavigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);

        // Set item selection listener for bottom navigation view
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.dashboard) {
                // Open dashboard activity
                startActivity(new Intent(getApplicationContext(), Dashboard.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (itemId == R.id.history) {
                // Open locate activity
                startActivity(new Intent(getApplicationContext(), Locate.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (itemId == R.id.profile) {
                // Stay on the user profile activity
                return true;
            }
            return false;
        });
    }

    public void navigateMessages(View view) {
        startActivity(new Intent(this, Message.class));
    }

    public void navigateLegal(View view) {
        startActivity(new Intent(this, Legal.class));
    }

    public void navigateVehicleConfig(View view) {
        startActivity(new Intent(this, VehicleConfiguration.class));
    }

}
