package com.example.bc_kitchen_project.home;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bc_kitchen_project.Help;
import com.example.bc_kitchen_project.R;
import com.example.bc_kitchen_project.ui.login.Fridge;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button btn_fridge = findViewById(R.id.btn_fridge);
        btn_fridge.setOnClickListener(this);
        Button btn_pantry = findViewById(R.id.btn_pantry);
        btn_pantry.setOnClickListener(this);
        Button btn_recipes = findViewById(R.id.btn_recipes);
        btn_recipes.setOnClickListener(this);
        Button btn_groceries = findViewById(R.id.btn_groceries);
        btn_groceries.setOnClickListener(this);
        Button btn_settings = findViewById(R.id.btn_settings);
        btn_settings.setOnClickListener(this);
        Button btn_help = findViewById(R.id.btn_help);
        btn_help.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fridge:
                Toast.makeText(this, "Fridge button clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent (this, Fridge.class);
                startActivity(intent);
                break;
            case R.id.btn_pantry:
                Toast.makeText(this, "Pantry button clicked", Toast.LENGTH_SHORT).show();

                break;
            case R.id.btn_recipes:
                Toast.makeText(this, "Recipes button clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_groceries:
                Toast.makeText(this, "Groceries button clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_settings:
                Toast.makeText(this, "Settings button clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_help:
                Toast.makeText(this, "Help button clicked", Toast.LENGTH_SHORT).show();
                Intent intentHelp = new Intent (this, Help.class);
                startActivity(intentHelp);
                break;
            default:
                break;
        }
    }

//    public void openFridge() {
//        Intent intent = new Intent(this, Fridge.class);
//        startActivity(intent);
//    }
}