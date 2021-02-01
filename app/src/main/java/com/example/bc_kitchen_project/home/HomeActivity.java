package com.example.bc_kitchen_project.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bc_kitchen_project.feedback.Feedback;
import com.example.bc_kitchen_project.Help;
import com.example.bc_kitchen_project.R;
import com.example.bc_kitchen_project.Fridge;

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
        Button btn_feedback = findViewById(R.id.btn_feedback);
        btn_feedback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fridge:
                Toast.makeText(this, "Fridge button clicked", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent (this, Fridge.class);
                startActivity(new Intent (this, Fridge.class));
                break;
            case R.id.btn_pantry:
                Toast.makeText(this, "Pantry button clicked", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent (this, Pantry.class));
                break;
            case R.id.btn_recipes:
                Toast.makeText(this, "Recipes button clicked", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent (this, Recipes.class));
                break;
            case R.id.btn_groceries:
                Toast.makeText(this, "Groceries button clicked", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent (this, Groceries.class));
                break;
            case R.id.btn_settings:
                Toast.makeText(this, "Settings button clicked", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent (this, Settings.class));
                break;
            case R.id.btn_help:
                Toast.makeText(this, "Help button clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent (this, Help.class));
                break;
            case R.id.btn_feedback:
                Toast.makeText(this, "Feedback button clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent (this, Feedback.class));
                break;
            default:
                break;
        }
    }


}