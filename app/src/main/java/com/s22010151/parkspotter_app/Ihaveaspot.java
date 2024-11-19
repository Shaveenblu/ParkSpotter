package com.s22010151.parkspotter_app;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Objects;

public class Ihaveaspot extends AppCompatActivity {

    // UI elements
    ImageView uploadimage;
    EditText place, charge, available, arrival;
    Button upload;
    String imageURL;
    Uri uri;


    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ihaveaspot);
        // Initialize UI elements
        uploadimage = findViewById(R.id.uploadimage);
        upload = findViewById(R.id.upload);
        place = findViewById(R.id.placetext);
        arrival = findViewById(R.id.arrivaltimetext);
        charge = findViewById(R.id.chargeperspotlayouttext);
        available = findViewById(R.id.availablespotslayouttext);

        bottomNavigationView = findViewById(R.id.bottomnavigation);
        bottomNavigationView.setSelectedItemId(R.id.history);



        // Set bottom navigation item selected listener
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
                startActivity(new Intent(getApplicationContext(), Legal.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });

        // Set up activity result launcher for image selection
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri = data.getData();
                            uploadimage.setImageURI(uri);
                        } else {
                            Toast.makeText(Ihaveaspot.this, "No image selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        // Set click listener for image upload
        uploadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        // Set click listener for data upload
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    // Method to save image to Firebase Storage
    public void saveData() {
        if (uri == null) {
            Toast.makeText(Ihaveaspot.this, "No Image Selected", Toast.LENGTH_SHORT).show();
            return;
        }
        StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child("Android Images").child(Objects.requireNonNull(uri.getLastPathSegment()));

        AlertDialog.Builder builder = new AlertDialog.Builder(Ihaveaspot.this);

        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) ;
                Uri urlImage = uriTask.getResult();
                imageURL = urlImage.toString();
                uploadData();
                dialog.dismiss();
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }

    // Method to upload data to Firebase Realtime Database
    public void uploadData() {
        String placet = place.getText().toString();
        String arrivalt = arrival.getText().toString();
        String charget = charge.getText().toString();
        String availablet = available.getText().toString();

        DataClass dataClass = new DataClass(placet, arrivalt, charget, availablet, imageURL);

        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        // Changed placet to currentdate

        FirebaseDatabase.getInstance().getReference("Ihaveaspot")
                .child(placet).setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    //.child(String.valueOf(place)).setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>()
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Ihaveaspot.this, "Saved", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Ihaveaspot.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
    public void goBackDashboard(View view) {
        startActivity(new Intent(this, Dashboard.class)); // Start Legal activity
    }
}

