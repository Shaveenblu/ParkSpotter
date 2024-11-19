
package com.s22010151.parkspotter_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_password extends AppCompatActivity {

    // UI elements
    Button btnReset;
    FirebaseAuth mAuth;
    TextInputEditText pass1, pass2;
    String strEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Initialize UI elements
        TextInputLayout passw1 = findViewById(R.id.passw1);
        pass1 = findViewById(R.id.pass1);
        TextInputLayout passw2 = findViewById(R.id.passw2);
        pass2 = findViewById(R.id.pass2);
        Button btnReset = findViewById(R.id.reset);
        TextInputEditText editEmail = findViewById(R.id.emailtext);

        mAuth = FirebaseAuth.getInstance();

        // Add text change listeners for password fields to check password strength
        pass1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Check password strength and provide feedback
                // (e.g., whether it's a strong or weak password)
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        pass2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Check password strength and provide feedback
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Button click listener for resetting password
        btnReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Get email input
                strEmail = editEmail.getText().toString().trim();
                if(!TextUtils.isEmpty(strEmail)){
                    // Call method to reset password
                    if(checkData()){
                        ResetPassword();
                    }
                } else {
                    editEmail.setError("Email field can't be empty");
                }

            }
        });

    }
    public boolean checkData() {

        String checkpass1 = pass1.getText().toString().trim();
        String checkpass2 = pass2.getText().toString().trim();

        if (checkpass1.isEmpty() || checkpass2.isEmpty()) {
            Toast.makeText(Forgot_password.this, "Both fields are required", Toast.LENGTH_SHORT).show();
        } else if (checkpass1.equals(checkpass2)) {
            return true;
        } else {
            Toast.makeText(Forgot_password.this, "Passwords does not match", Toast.LENGTH_SHORT).show();
            return false;

        }
        return false;

    }



    // Method to reset password
    private void ResetPassword() {
        mAuth.sendPasswordResetEmail(strEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                // Show success message and navigate to sign-in screen

                Toast.makeText(Forgot_password.this, "Reset Password Link has been sent to the mailbox", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Forgot_password.this, Signin.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Show failure message
                Toast.makeText(Forgot_password.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Method to navigate to sign-in screen
    public void signin(View view) {
        startActivity(new Intent(this, Signin.class));
    }
}
