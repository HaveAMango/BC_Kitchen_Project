package com.example.bc_kitchen_project.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bc_kitchen_project.Feedback;
import com.example.bc_kitchen_project.GroceryList;
import com.example.bc_kitchen_project.Help;
import com.example.bc_kitchen_project.Pantry;
import com.example.bc_kitchen_project.R;
import com.example.bc_kitchen_project.Fridge;
import com.example.bc_kitchen_project.findRecipe.FindRecipe;
import com.example.bc_kitchen_project.settings.Settings;

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
                startActivity(new Intent (this, Fridge.class));
                break;
            case R.id.btn_pantry:
                startActivity(new Intent (this, Pantry.class));
                break;
            case R.id.btn_recipes:
                startActivity(new Intent (this, FindRecipe.class));
                break;
            case R.id.btn_groceries:
                startActivity(new Intent (this, GroceryList.class));
                break;
            case R.id.btn_settings:
                startActivity(new Intent (this, Settings.class));
                break;
            case R.id.btn_help:
                startActivity(new Intent (this, Help.class));
                break;
            case R.id.btn_feedback:
                startActivity(new Intent (this, Feedback.class));
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