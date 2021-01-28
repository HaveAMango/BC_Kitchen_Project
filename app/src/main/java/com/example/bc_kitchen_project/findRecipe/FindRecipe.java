package com.example.bc_kitchen_project.findRecipe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bc_kitchen_project.R;
import com.google.android.material.textfield.TextInputLayout;

public class FindRecipe extends AppCompatActivity implements View.OnClickListener {

    Button findRecButton;
    TextInputLayout inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_recipe);
        findRecButton = (Button) findViewById(R.id.findRecipeButton); //Initializing Button and InputField
        inputText = (TextInputLayout) findViewById(R.id.findRecipeInput);
        findRecButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this, "Finding Recipies...", Toast.LENGTH_LONG).show();
        String ingredients = inputText.getEditText().getText().toString(); //Gets text from InputField
        connect(ingredients);
    }

    //Makes connection to Google search with given ingredients
    void connect(String ingredients) {
        //Uri uriUrl = Uri.parse("http://google.com/"); //##These two commented lines are for "JUST IN CASE" if something
        //Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl); //##similar will have to be done
        Intent search = new Intent(Intent.ACTION_WEB_SEARCH); //Makes new Google search window
        search.putExtra(SearchManager.QUERY, (ingredients + " recipe")); //Makes search text/query
        startActivity(search);
    }
}