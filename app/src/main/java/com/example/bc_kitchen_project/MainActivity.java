package com.example.bc_kitchen_project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

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
        Intent intent = new Intent(this, HomeActivity.class);
        this.startActivity(intent);
        Button logoutBtn = findViewById(R.id.button_logout);
        logoutBtn.setOnClickListener(v -> {
            LoginRepository.getInstance().logout();
            redirectToWelcomeScreen(this);
        });

        ImageButton btn = findViewById(R.id.broccoliButton);
        btn.setOnClickListener(v -> {
            redirectTo(WelcomeActivity.class);
        });

        //Trigger LoginDataSource initialization
        LoginRepository.getInstance();
    }

    private void redirectTo(Class<? extends Activity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    public static void onLoginHandled() {
        LoginRepository.getInstance().loadCachedUser();
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }
}