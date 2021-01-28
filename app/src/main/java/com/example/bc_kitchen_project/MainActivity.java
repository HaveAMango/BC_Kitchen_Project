package com.example.bc_kitchen_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bc_kitchen_project.data.LoginRepository;
import com.example.bc_kitchen_project.ui.login.WelcomeActivity;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        if (!LoginRepository.getInstance().isLoggedIn()) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
        }
    }

    public static Context getContext() {
        return getContext();
    }
}