package com.example.bc_kitchen_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Help extends AppCompatActivity {

    RelativeLayout helpAct;
    TextView helpHeadIntro, helpFridge, helpHeadPantry, helpHeadRecipes, helpHeadGroceries, helpHeadSettings, helpHeadFeedback;
    TextView helpIntroText, helpFridgeText, helpPantryText, helpRecipesText, helpGroceriesText, helpSettingsText, helpFeedbackText;
    ArrayList<TextView> orangeHeadings = new ArrayList<TextView>();
    ArrayList<TextView> helpText = new ArrayList<TextView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        setTitle("Help");
        helpAct = findViewById(R.id.helpActivity);
        initializeTextViews();
        loadSettings();

    }

    private void initializeTextViews() {
        helpHeadIntro = findViewById(R.id.helpHeadIntro);
        helpFridge = findViewById(R.id.helpFridge);
        orangeHeadings.add(helpFridge);
        helpHeadPantry = findViewById(R.id.helpHeadPantry);
        orangeHeadings.add(helpHeadPantry);
        helpHeadRecipes = findViewById(R.id.helpHeadRecipes);
        orangeHeadings.add(helpHeadRecipes);
        helpHeadGroceries = findViewById(R.id.helpHeadGroceries);
        orangeHeadings.add(helpHeadGroceries);
        helpHeadSettings = findViewById(R.id.helpHeadSettings);
        orangeHeadings.add(helpHeadSettings);
        helpHeadFeedback = findViewById(R.id.helpHeadFeedback);
        orangeHeadings.add(helpHeadFeedback);

        helpIntroText = findViewById(R.id.helpIntroText);
        helpText.add(helpIntroText);
        helpFridgeText = findViewById(R.id.helpFridgeText);
        helpText.add(helpFridgeText);
        helpPantryText = findViewById(R.id.helpPantryText);
        helpText.add(helpPantryText);
        helpRecipesText = findViewById(R.id.helpRecipesText);
        helpText.add(helpRecipesText);
        helpGroceriesText = findViewById(R.id.helpGroceriesText);
        helpText.add(helpGroceriesText);
        helpSettingsText = findViewById(R.id.helpSettingsText);
        helpText.add(helpSettingsText);
        helpFeedbackText = findViewById(R.id.helpFeedbackText);
        helpText.add(helpFeedbackText);
    }

    //Loads Settings
    private void loadSettings() {
        //Color Schemes
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean cNight = sp.getBoolean("NIGHT", false);
        //What colors in Night Mode
        if (cNight) {
            helpAct.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.backgroundNight, null));
            helpHeadIntro.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.secondaryButtonNight, null));
            for (TextView tv : orangeHeadings)
                tv.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryNight, null));
                //tv.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryNight, null));
            for (TextView tv : helpText)
                tv.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
            //tv.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        }
        //What colors when Night Mode is off
        else {
            helpAct.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
            //tv.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryText, null));
        }
    }

    @Override
    protected void onResume() {
        loadSettings();
        super.onResume();
    }
}