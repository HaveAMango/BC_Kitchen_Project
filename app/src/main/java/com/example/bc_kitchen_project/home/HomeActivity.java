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
import com.example.bc_kitchen_project.MainActivity;
import com.example.bc_kitchen_project.Pantry;
import com.example.bc_kitchen_project.data.LoginRepository;
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

        addButton(R.id.btn_fridge);
        addButton(R.id.btn_pantry);
        addButton(R.id.btn_recipes);
        addButton(R.id.btn_groceries);
        addButton(R.id.btn_settings);
        addButton(R.id.btn_help);
        addButton(R.id.btn_feedback);
        addButton(R.id.btn_logout);

        mainAct = findViewById(R.id.HomeActivity);
        //loadSettings();
    }

    private void addButton(int id) {
        Button btn = findViewById(id);
        btn.setOnClickListener(this);
        buttons.add(btn);
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
            case R.id.btn_logout:
                LoginRepository.getInstance().logout();

                intent = new Intent(this, MainActivity.class);
                startActivity(intent);

                setResult(RESULT_OK);
                finish();
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