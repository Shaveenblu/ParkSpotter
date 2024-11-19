//Navigation
package com.s22010151.parkspotter_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.s22010151.parkspotter_app.databinding.ActivityLegalBinding;
import com.s22010151.parkspotter_app.databinding.ActivityMainBinding;
import com.s22010151.parkspotter_app.databinding.ActivityNavigationBinding;

public class Navigation extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_navigation);

        bottomNavigationView = findViewById(R.id.bottomnavigation);
        bottomNavigationView.setSelectedItemId(R.id.dashboard);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.dashboard) {

                return true;
            } else if (itemId == R.id.history) {
                startActivity(new Intent(getApplicationContext(), Locate.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;

            } else if (itemId == R.id.profile) {
                startActivity(new Intent(getApplicationContext(), Legal.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;

            }
            return false;

        });



    }

}