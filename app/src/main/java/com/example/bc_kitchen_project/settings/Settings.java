package com.example.bc_kitchen_project.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bc_kitchen_project.R;

public class Settings extends AppCompatActivity implements View.OnClickListener {
    Button textSizeButton;
    Button colorSchemeButton;
    Button deleteAccButton;
    Button logOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        textSizeButton = (Button) findViewById(R.id.TextSizeButton);
        textSizeButton.setOnClickListener(this);
        colorSchemeButton = (Button) findViewById(R.id.ColorSchemeButton);
        colorSchemeButton.setOnClickListener(this);
        deleteAccButton = (Button) findViewById(R.id.DeleteAccountButton);
        deleteAccButton.setOnClickListener(this);
        logOutButton = (Button) findViewById(R.id.LogOutButton);
        logOutButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.TextSizeButton: {
                Toast.makeText(this, "TextSize Button", Toast.LENGTH_LONG).show();
                break;
            }

            case R.id.ColorSchemeButton: {
                Toast.makeText(this, "ColorScheme Button", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.DeleteAccountButton: {
                Toast.makeText(this, "DeleteAcc Button", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.LogOutButton: {
                Toast.makeText(this, "LogOut Button", Toast.LENGTH_LONG).show();
                break;
            }
        }
    }
}