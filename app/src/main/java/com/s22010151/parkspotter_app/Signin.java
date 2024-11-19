
package com.s22010151.parkspotter_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Signin extends AppCompatActivity {

    // UI elements
    TextInputEditText editTextUsername, editTextPassword;
    Button buttonLogin;

    // Firebase
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://parkspotter-423207-default-rtdb.firebaseio.com");

        // Initialize UI elements
        editTextUsername = findViewById(R.id.username2);
        editTextPassword = findViewById(R.id.password1);
        buttonLogin = findViewById(R.id.signin);

        // Set click listener for login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get username and password
                String username = String.valueOf(editTextUsername.getText());
                String password = String.valueOf(editTextPassword.getText());

                // Validate username and password
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(Signin.this, "Enter Username", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Signin.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    // Check if user exists in the database
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(username)) {
                                // Check if password is correct
                                String getPassword = snapshot.child(username).child("password").getValue(String.class);
                                if (getPassword.equals(password)) {
                                    // Login successful
                                    Toast.makeText(Signin.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Signin.this, Dashboard.class));
                                    finish();
                                } else {
                                    // Incorrect password
                                    Toast.makeText(Signin.this, "Wrong credentials", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // User not found
                                Toast.makeText(Signin.this, "User not found", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle database error
                        }
                    });
                }
            }
        });
    }

    // Open signup activity
    public void signup(View view) {
        startActivity(new Intent(this, Signup.class));
    }

    // Open forgot password activity
    public void forgotpass(View view) {
        startActivity(new Intent(this, Forgot_password.class));
    }

    // Open dashboard activity
    public void dashboard(View view) {
        startActivity(new Intent(this, Dashboard.class));
    }
}
