package com.example.bc_kitchen_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bc_kitchen_project.home.HomeActivity;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Ratings extends AppCompatActivity {

    RecyclerView recyclerView;
    RatingAdapter ratingAdapter;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);
        setTitle("Ratings");

        // Create a instance of the database and get
        // its reference
        database = FirebaseDatabase.getInstance().getReference("ratings");
        recyclerView = findViewById(R.id.commentRecyclerView);

        // To display the Recycler view linearly
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<Rating> options
                = new FirebaseRecyclerOptions.Builder<Rating>()
                .setQuery(database, Rating.class)
                .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        ratingAdapter = new RatingAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(ratingAdapter);
    }

    // Function to tell the app to start getting
    // data from database on starting of the activity
    @Override protected void onStart() {
        super.onStart();
        ratingAdapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stoping of the activity
    @Override protected void onStop() {
        super.onStop();
        ratingAdapter.stopListening();
    }



//        Button showRatings = findViewById(R.id.showRatings);
//        showRatings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                database.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for (DataSnapshot product : snapshot.getChildren()) {
//                            String name = product.child("name").getValue().toString().trim();
//                            String comment = product.child("comment").getValue().toString().trim();
//                            float ratingValue = (float) product.child("rating_value").getValue();
//
//                            Toast.makeText(Ratings.this, name + ": " + comment + ", rating: " + ratingValue, Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Log.e("ratings", "error: " + error);
//                    }
//
//                });
//            }
//
//        });



    @Override
    public void onBackPressed() { //makes sure, that is user presses "back" from Fridge, goes to main
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}