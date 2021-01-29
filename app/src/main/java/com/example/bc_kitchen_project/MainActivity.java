package com.example.bc_kitchen_project;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bc_kitchen_project.settings.Settings;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        String findRecipe;
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
}