package com.example.bc_kitchen_project.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.example.bc_kitchen_project.R;
import com.example.bc_kitchen_project.data.LoginRepository;
import com.example.bc_kitchen_project.home.HomeActivity;

public class WelcomeActivity extends AppCompatActivity {

    private ConstraintLayout welcomeAct;
    Button loginButton, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        welcomeAct = findViewById(R.id.welocomeActivity);
        loginButton = findViewById(R.id.button_sign_in);
        registerButton = findViewById(R.id.button_register);
        loadSettings();

        loginButton.setOnClickListener(v -> redirectTo(LoginActivity.class));
        registerButton.setOnClickListener(v -> redirectTo(RegisterActivity.class));

        if (LoginRepository.getInstance().isLoggedIn()) {
            redirectTo(HomeActivity.class);
        }
    }

    private void redirectTo(Class<? extends Activity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);

        setResult(Activity.RESULT_OK);
        finish();
    }

    //Loads Settings
    private void loadSettings() {
        //Color Schemes
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean cNight = sp.getBoolean("NIGHT", false);
        //What colors in Night Mode
        if (cNight) {
            welcomeAct.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.backgroundNight, null));
            loginButton.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.secondaryButtonNight, null));
            registerButton.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.secondaryButtonNight, null));
        }
    }

    @Override
    protected void onResume() {
        loadSettings();
        super.onResume();
    }
}