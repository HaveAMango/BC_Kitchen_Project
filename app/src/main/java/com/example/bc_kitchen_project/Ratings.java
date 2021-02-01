package com.example.bc_kitchen_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class Ratings extends AppCompatActivity {

    RecyclerView ratingsRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

        ratingsRecyclerview = findViewById(R.id.ratingsRecyclerview);




    }
}