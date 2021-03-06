package com.example.bc_kitchen_project.feedback;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.bc_kitchen_project.R;
import com.example.bc_kitchen_project.feedback.Rating;
import com.example.bc_kitchen_project.feedback.RatingAdapter;
import com.example.bc_kitchen_project.home.HomeActivity;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Ratings extends AppCompatActivity {

    RecyclerView recyclerView;
    RatingAdapter ratingAdapter;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);
        setTitle("Ratings");

        // Create a instance of the database and get its reference
        database = FirebaseDatabase.getInstance().getReference("ratings");
        recyclerView = findViewById(R.id.commentRecyclerView);

        // To display the Recycler view linearly
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // It is a class provide by the FirebaseUI to make a query in the database to fetch appropriate data
        FirebaseRecyclerOptions<Rating> options
                = new FirebaseRecyclerOptions.Builder<Rating>()
                .setQuery(database, Rating.class)
                .build();
        // Connecting object of required Adapter class to the Adapter class itself
        ratingAdapter = new RatingAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(ratingAdapter);
    }

    // Function to tell the app to start getting data from database on starting of the activity
    @Override protected void onStart() {
        super.onStart();
        ratingAdapter.startListening();
    }

    // Function to tell the app to stop getting data from database on stoping of the activity
    @Override protected void onStop() {
        super.onStop();
        ratingAdapter.stopListening();
    }

    @Override
    public void onBackPressed() { //makes sure, that is user presses "back" from Ratings, goes to Feedback
        Intent intent = new Intent(this, Feedback.class);
        startActivity(intent);
    }
}