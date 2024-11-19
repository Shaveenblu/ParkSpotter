//Update
package com.s22010151.parkspotter_app;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class Update extends AppCompatActivity {

    ImageView updateImage;
    Button updateButton;
    EditText updatePlace, updateCharge, updateAvailable, updateArrival;
    String place, arrival, charge, available;
    String imageUrl;
    String key, oldImageURL;
    Uri uri;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        updateButton = findViewById(R.id.updatebtn);
        updatePlace = findViewById(R.id.updatePlacetext);
        updateArrival = findViewById(R.id.updateArrivaltimetext);
        updateCharge = findViewById(R.id.updateChargeperspotlayouttext);
        updateAvailable = findViewById(R.id.updateAvailablespotslayouttext);
        updateImage = findViewById(R.id.updateimage);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            updateImage.setImageURI(uri);
                        } else {
                            Toast.makeText(Update.this, "No image selected",Toast.LENGTH_SHORT).show();
                        }

                    }
                }
        );
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            Glide.with(Update.this).load(bundle.getString("Image")).into(updateImage);
            updatePlace.setText(bundle.getString("Place"));
            updateArrival.setText(bundle.getString("Arrival"));
            updateCharge.setText(bundle.getString("Charge"));
            updateAvailable.setText(bundle.getString("available"));
            key = bundle.getString("Key");
            oldImageURL = bundle.getString("Image");

        }
        databaseReference = FirebaseDatabase.getInstance().getReference("Ihaveaspot").child(key);

        updateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                Intent intent = new Intent(Update.this, Ihaveaspot.class);
                startActivity(intent);
            }
        });

    }
    public void saveData() {
        if (uri == null) {
            Toast.makeText(Update.this, "No Image Selected", Toast.LENGTH_SHORT).show();
            return;
        }
        storageReference = FirebaseStorage.getInstance().getReference().child("Ihaveaspot").child(Objects.requireNonNull(uri.getLastPathSegment()));

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageUrl = urlImage.toString();
                updateData();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    public void updateData() {
        place = updatePlace.getText().toString().trim();
        arrival = updateArrival.getText().toString().trim();
        charge = updateCharge.getText().toString().trim();
        available = updateAvailable.getText().toString().trim();


        DataClass dataClass = new DataClass(place, arrival,charge, available,imageUrl);


        databaseReference.setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(oldImageURL);
                    reference.delete();
                    Toast.makeText(Update.this, "Updated",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Update.this, e.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void gobackDasboardfromUpdate(View view) {
        startActivity(new Intent(this, Dashboard.class));
    }
}