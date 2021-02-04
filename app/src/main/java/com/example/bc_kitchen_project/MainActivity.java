package com.example.bc_kitchen_project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.example.bc_kitchen_project.data.LoginRepository;
import com.example.bc_kitchen_project.ui.login.WelcomeActivity;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    private static MainActivity instance;
    ConstraintLayout mainAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainAct = findViewById(R.id.mainActivity);
        loadSettings();
        FirebaseApp.initializeApp(this);
        instance = this;

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

    //Loads Settings
    private void loadSettings() {
        //Color Schemes
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean cNight = sp.getBoolean("NIGHT", false);
        //What colors in Night Mode
        if (cNight) {
            mainAct.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.backgroundNight, null));
        }
    }

    @Override
    protected void onResume() {
        loadSettings();
        super.onResume();
    }
}