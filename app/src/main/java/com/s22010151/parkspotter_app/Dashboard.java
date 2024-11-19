
package com.s22010151.parkspotter_app;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
//import com.s22010151.parkspotter_app.databinding.ActivityMainBinding;

public class Dashboard extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView; // Declaring MapView variable
    FirebaseAuth auth; // Firebase Authentication instance
    FirebaseUser user; // Firebase User instance
    DatabaseReference reference;

    String username;

    Button ihaveaspotButton; // Button for "I have a spot"
    Button navigateFindaspotButton; // Button for "Find a spot"
    Button navigateDistanceButton; // Button for "Map"
    Button navigaterendingSpacesButton;

    private BottomNavigationView bottomNavigationView; // Bottom navigation view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard); // Set the layout for this activity

        bottomNavigationView = findViewById(R.id.bottomnavigation); // Initialize bottom navigation view
        bottomNavigationView.setSelectedItemId(R.id.dashboard); // Set the selected item in the bottom navigation view

        // Set a listener for bottom navigation view item selection
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId(); // Get the selected item ID

            // Check which item is selected and perform corresponding actions
            if (itemId == R.id.dashboard) {
                return true; // Return true to indicate the selection was handled
            } else if (itemId == R.id.history) {
                startActivity(new Intent(getApplicationContext(), Locate.class)); // Start Locate activity
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

        mapView = findViewById(R.id.map_view); // Initialize MapView
        mapView.onCreate(savedInstanceState); // Create MapView
        mapView.getMapAsync(this); // Set OnMapReadyCallback






        // Set OnClickListener for logout button
        //      button.setOnClickListener(new View.OnClickListener() {
        //        @Override
        //      public void onClick(View v) {
        //        FirebaseAuth.getInstance().signOut(); // Sign out the current user
        //      Intent intent = new Intent(getApplicationContext(), Signin.class); // Create intent to start Signin activity
        //    startActivity(intent); // Start Signin activity
        //  finish(); // Finish the current activity
        //}
        // });
    }

    // Method invoked when the map is ready
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Customize the map here if needed
        // For example, set a specific location
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.5074, -0.1278), 10));

    }

    // Method to add menu provider
//    @Override
    public void addMenuProvider(@NonNull MenuProvider provider, @NonNull LifecycleOwner owner, @NonNull Lifecycle.State state) {
        // Not used in this implementation
    }

    // Method to handle the "I Have a Spot" button click
    public void ihaveaspot(View view) {
        startActivity(new Intent(this, Ihaveaspot.class)); // Start Ihaveaspot activity
    }

    // Method to handle the "Maps Activity" button click


    // Method to handle the "Navigation Activity" button click
    public void navigationActivity(View view) {
        startActivity(new Intent(this, VehicleConfiguration.class)); // Start Legal activity
    }
    public void navigationLocation(View view) {
        startActivity(new Intent(this, Locatetwoplaces.class));
    }
    public void navigateFindaspot(View view) {
        startActivity(new Intent(this, Findaspot.class)); // Start Legal activity
    }
    public void navigaterendingSpaces(View view) {
        startActivity(new Intent(this, History.class)); // Start Legal activity
    }
    public void navigateDistance(View view) {
        startActivity(new Intent(this, Distance.class)); // Start Legal activity
    }
}
