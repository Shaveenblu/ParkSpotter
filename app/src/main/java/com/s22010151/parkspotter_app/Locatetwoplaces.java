package com.s22010151.parkspotter_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Locatetwoplaces extends AppCompatActivity {


    private EditText start;
    private EditText end;
    private Button searchDistance;
    private BottomNavigationView bottomNavigationView; // Bottom navigation view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locatetwoplaces);

        bottomNavigationView = findViewById(R.id.bottomnavigation); // Initialize bottom navigation view
        bottomNavigationView.setSelectedItemId(R.id.dashboard); // Set the selected item in the bottom navigation view

        // Set a listener for bottom navigation view item selection
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId(); // Get the selected item ID

            // Check which item is selected and perform corresponding actions
            if (itemId == R.id.dashboard) {
                return true; // Return true to indicate the selection was handled
            } else if (itemId == R.id.history) {
                startActivity(new Intent(getApplicationContext(), Locate.class)); // Start History activity
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Apply transition animation
                finish(); // Finish the current activity
                return true; // Return true to indicate the selection was handled

            } else if (itemId == R.id.profile) {
                startActivity(new Intent(getApplicationContext(), UserProfile.class)); // Start UserProfile activity
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Apply transition animation
                finish(); // Finish the current activity
                return true; // Return true to indicate the selection was handled
            }
            return false; // Return false if the selection was not handled
        });

        start = findViewById(R.id.startLocation);
        end = findViewById(R.id.endLocation);
        searchDistance = findViewById(R.id.searchDistance);

        searchDistance.setOnClickListener(v -> {
            String startingPoint = start.getText().toString();
            String endpoint = end.getText().toString();

            if(startingPoint.equals("") || endpoint.equals("")){
                Toast.makeText(this,"Please enter starting location and destination",
                        Toast.LENGTH_SHORT).show();
            } else {
                getPath(startingPoint, endpoint);
            }
        });
    }
    private void getPath(String startingPoint, String endpoint) {
        try{
            Uri uri = Uri.parse("https://www.google.com/maps/dir/" + startingPoint + "/" + endpoint);
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }catch (ActivityNotFoundException exception){
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps&hl=de&pli=1");
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public void mapsActivity(View view) {
        startActivity(new Intent(this, MapActivity.class)); // Start MapsActivity
    }
}