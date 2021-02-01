package com.example.bc_kitchen_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bc_kitchen_project.data.LoginRepository;
import com.example.bc_kitchen_project.ui.login.WelcomeActivity;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    private static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        instance = this;
        Intent intent = new Intent(this, Home.class);
        this.startActivity(intent);
        Button logoutBtn = findViewById(R.id.button_logout);
        logoutBtn.setOnClickListener(v -> {
            LoginRepository.getInstance().logout();
            redirectToWelcomeScreen(this);
        });

        //Trigger LoginDataSource initialization
        LoginRepository.getInstance();
    }

    private void redirectToWelcomeScreen(MainActivity mainActivity) {
        Intent intent = new Intent(mainActivity, WelcomeActivity.class);
        this.startActivity(intent);
    }

    public static void onLoginHandled() {
        LoginRepository.getInstance().loadCachedUser();
        if (!LoginRepository.getInstance().isLoggedIn()) {
            instance.redirectToWelcomeScreen(instance);
        }
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }
}