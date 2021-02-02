package com.example.bc_kitchen_project.home;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.example.bc_kitchen_project.GroceryList;
import com.example.bc_kitchen_project.Pantry;
import com.example.bc_kitchen_project.feedback.Feedback;
import com.example.bc_kitchen_project.Help;
import com.example.bc_kitchen_project.R;
import com.example.bc_kitchen_project.Fridge;
import com.example.bc_kitchen_project.findRecipe.FindRecipe;
import com.example.bc_kitchen_project.settings.Settings;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout mainAct;
    Intent intent;
    private ArrayList<Button> buttons = new ArrayList<Button>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button btn_fridge = findViewById(R.id.btn_fridge);
        btn_fridge.setOnClickListener(this);
        buttons.add(btn_fridge);
        Button btn_pantry = findViewById(R.id.btn_pantry);
        btn_pantry.setOnClickListener(this);
        buttons.add(btn_pantry);
        Button btn_recipes = findViewById(R.id.btn_recipes);
        btn_recipes.setOnClickListener(this);
        buttons.add(btn_recipes);
        Button btn_groceries = findViewById(R.id.btn_groceries);
        btn_groceries.setOnClickListener(this);
        buttons.add(btn_groceries);
        Button btn_settings = findViewById(R.id.btn_settings);
        btn_settings.setOnClickListener(this);
        buttons.add(btn_settings);
        Button btn_help = findViewById(R.id.btn_help);
        btn_help.setOnClickListener(this);
        buttons.add(btn_help);
        Button btn_feedback = findViewById(R.id.btn_feedback);
        btn_feedback.setOnClickListener(this);
        buttons.add(btn_feedback);
        mainAct = findViewById(R.id.HomeActivity);
        //loadSettings();
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
    //LoadsSettings
    private void loadSettings(){
        //Color Schemes
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean cNight = sp.getBoolean("NIGHT", false);
        //What colors in Night Mode
        if (cNight) {
            mainAct.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
            for(Button btn: buttons){
                //Button Background and Text color
                btn.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.buttonNight, null));
                btn.setTextColor(ResourcesCompat.getColor(getResources(), R.color.buttonTextNight, null));
            }
        }
        //What colors when Night Mode is off
        else {
            mainAct.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
            for(Button btn: buttons){
                btn.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.button, null));
                btn.setTextColor(ResourcesCompat.getColor(getResources(), R.color.buttonText, null));
            }
        }

        //Screen orientation changes
        String orient = sp.getString("ORIENTATION", "false");
        if ("1".equals(orient)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
        } else if ("2".equals(orient)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else if ("3".equals(orient)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    @Override
    protected void onResume() {
        //loadSettings();
        super.onResume();
    }
}