package com.example.bc_kitchen_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bc_kitchen_project.home.HomeActivity;
import com.example.bc_kitchen_project.ui.login.LoginActivity;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}