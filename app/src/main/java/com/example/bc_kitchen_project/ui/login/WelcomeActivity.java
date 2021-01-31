package com.example.bc_kitchen_project.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.bc_kitchen_project.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button loginButton = findViewById(R.id.button_sign_in);
        Button registerButton = findViewById(R.id.button_register);

        loginButton.setOnClickListener(v -> redirectTo(LoginActivity.class));
        registerButton.setOnClickListener(v -> redirectTo(RegisterActivity.class));
    }

    private void redirectTo(Class<? extends Activity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);

        setResult(Activity.RESULT_OK);
        finish();
    }
}