
package com.s22010151.parkspotter_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Details extends AppCompatActivity {

    TextView detailPlace, detailCharge, detailArrival, detailSpot;
    ImageView detailImage;
    FloatingActionButton deletebtn, editbtn;
    String key = "";
    String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        detailPlace = findViewById(R.id.detailPlace);
        detailImage = findViewById(R.id.detailImage);
        detailArrival = findViewById(R.id.detailArrival);
        detailCharge = findViewById(R.id.detailCharge);
        detailSpot = findViewById(R.id.detailSpots);
        deletebtn = findViewById(R.id.deletebtn);
        editbtn = findViewById(R.id.editbtn);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            detailPlace.setText(bundle.getString("arrival"));
            detailCharge.setText(bundle.getString("available"));
            detailArrival.setText(bundle.getString("charge"));
            detailSpot.setText(bundle.getString("place"));
            key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);
        }

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Ihaveaspot");
                FirebaseStorage storage = FirebaseStorage.getInstance();

                // if (imageUrl != null && !imageUrl.isEmpty()) {
                StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        reference.child(key).removeValue();
                        Toast.makeText(Details.this, "Deleted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Dashboard.class));
                        finish();
                    }
                });
                // else {
                // Handle case where imageUrl is null or empty
                //    Toast.makeText(Details.this, "Image URL is null or empty", Toast.LENGTH_SHORT).show();
                // }
            }
        });

        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Details.this,  Update.class)
                        .putExtra("Title", detailPlace.getText().toString())
                        .putExtra("Description", detailCharge.getText().toString())
                        .putExtra("Arrival", detailArrival.getText().toString())
                        .putExtra("Spots", detailSpot.getText().toString())
                        .putExtra("Image",imageUrl)
                        .putExtra("Key",key);
                startActivity(intent);
            }
        });
    }
}