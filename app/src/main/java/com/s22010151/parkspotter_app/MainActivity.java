package com.s22010151.parkspotter_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Using a Handler to delay the start of the Signin activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Create an intent to start the Signin activity
                Intent intent = new Intent(MainActivity.this, Signin.class);
                startActivity(intent);
                // Set the content view to the activity_main layout (this might be a mistake, as it's setting the content view after starting the Signin activity)
                setContentView(R.layout.activity_main);
            }
        }, 1000); // Delay for 1 second (1000 milliseconds)
    }
}
