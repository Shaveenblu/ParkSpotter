
package com.s22010151.parkspotter_app;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Signup extends AppCompatActivity {

    // UI elements
    TextInputEditText editTextUsername, editTextPassword, editTextEmail;
    Button buttonReg;

    // Firebase
    FirebaseAuth nAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize Firebase
        nAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://parkspotter-423207-default-rtdb.firebaseio.com");

        // Initialize UI elements
        editTextUsername = findViewById(R.id.userinput);
        editTextPassword = findViewById(R.id.passinput);
        editTextEmail = findViewById(R.id.emaillayout);
        buttonReg = findViewById(R.id.signup);

        // Set click listener for register button
        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                String email, password, username;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());
                username = String.valueOf(editTextUsername.getText());

                // Validate input fields
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Signup.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Signup.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(username)) {
                    Toast.makeText(Signup.this, "Enter username", Toast.LENGTH_SHORT).show();
                    return;
                } else if (password.length() < 6) {
                    editTextPassword.setError("Password must contain 6 characters");
                    return;
                } else {
                    // Check if user already exists
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(username)) {
                                // User already registered
                                Toast.makeText(Signup.this, "User already registered", Toast.LENGTH_SHORT).show();
                            } else {
                                // Register the user
                                databaseReference.child("users").child(username).child("email").setValue(email);
                                databaseReference.child("users").child(username).child("password").setValue(password);
                                Toast.makeText(Signup.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                finish();
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

    // Open signin activity
    public void signin(View view) {
        startActivity(new Intent(this, Signin.class));
    }

    // Open dashboard activity
    public void dashboard(View view) {
        startActivity(new Intent(this, Dashboard.class));
    }
}

