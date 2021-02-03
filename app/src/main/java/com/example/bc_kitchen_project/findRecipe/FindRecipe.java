package com.example.bc_kitchen_project.findRecipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bc_kitchen_project.R;
import com.google.android.material.textfield.TextInputLayout;

public class FindRecipe extends AppCompatActivity implements View.OnClickListener {

    Button findRecButton;
    TextInputLayout inputText;
    ConstraintLayout findRecAct;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_recipe);
        findRecAct = findViewById(R.id.findRecipeActivity);
        findRecButton = (Button) findViewById(R.id.findRecipeButton); //Initializing Button and InputField
        inputText = (TextInputLayout) findViewById(R.id.findRecipeInput);
        findRecButton.setOnClickListener(this);
        tv = findViewById(R.id.findRecipeDescription);
        loadSettings();
    }

    @Override
    public void onClick(View view) {
        String ingredients = inputText.getEditText().getText().toString(); //Gets text from InputField
        if ("".equals(ingredients.trim())) { //Checks if user entered any value
            Toast.makeText(this, "You did not enter any ingredients", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Finding Recipies...", Toast.LENGTH_LONG).show();
            findRecipe(ingredients);
        }
    }

    //Makes connection to Google search with given ingredients
    void findRecipe(String ingredients) {
        //Uri uriUrl = Uri.parse("http://google.com/"); //##These two commented lines are for "JUST IN CASE" if something
        //Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl); //##similar will have to be done
        Intent search = new Intent(Intent.ACTION_WEB_SEARCH); //Makes new Google search window
        search.putExtra(SearchManager.QUERY, (ingredients.trim() + " recipe")); //Makes search text/query
        startActivity(search);
    }

    //Loads Settings
    private void loadSettings() {
        //Color Schemes
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean cNight = sp.getBoolean("NIGHT", false);
        //What colors in Night Mode
        if (cNight) {
            findRecAct.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.backgroundNight, null));
            findRecButton.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.secondaryButtonNight, null));
            tv.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        }
        //What colors when Night Mode is off
        else {
            findRecAct.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
            findRecButton.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
            tv.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryText, null));
        }
    }

    @Override
    protected void onResume() {
        loadSettings();
        super.onResume();
    }
}