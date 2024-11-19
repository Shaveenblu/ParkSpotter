
package com.s22010151.parkspotter_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Legal extends AppCompatActivity {

    // UI elements
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal);

        // Initialize and set up the VideoView
        VideoView appIntro = findViewById(R.id.appIntro);
        appIntro.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.appintro);
        appIntro.start();

        // Set media controller for the VideoView
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(appIntro);
        appIntro.setMediaController(mediaController);

        // Set up bottom navigation view
        bottomNavigationView = findViewById(R.id.bottomnavigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            // Handle bottom navigation item selection
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
    public void gobackProfile(View view) {
        startActivity(new Intent(this, UserProfile.class)); // Start Legal activity
    }
}

