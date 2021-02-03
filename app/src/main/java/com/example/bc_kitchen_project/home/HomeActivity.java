package com.example.bc_kitchen_project.home;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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

    private static final String TAG = "HomenaCtIvOItY";
    private RelativeLayout mainAct;
    Intent intent;
    private ArrayList<Button> mainButtons = new ArrayList<Button>();
    private ArrayList<Button> secondaryButtons = new ArrayList<Button>();

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
        loadSettings();
    }

    private void addButton(int id) {
        Button btn = findViewById(id);
        btn.setOnClickListener(this);
        checkButtonAndAddToList(btn);
    }

    private void checkButtonAndAddToList(Button btn){
        if(mainButtons.size()<4){
            mainButtons.add(btn);
        }else{
            secondaryButtons.add(btn);
        }
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
            mainAct.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.backgroundNight, null));
            for(Button btn: mainButtons){
                //Button Background and Text color
                btn.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.mainButtonNight, null));
                btn.setTextColor(ResourcesCompat.getColor(getResources(), R.color.buttonTextNight, null));
            }
            for(Button btn: secondaryButtons){
                //Button Background and Text color
                btn.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.secondaryButtonNight, null));
                btn.setTextColor(ResourcesCompat.getColor(getResources(), R.color.buttonTextNight, null));
            }
        }
        //What colors when Night Mode is off
        else {
            mainAct.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
            for(Button btn: mainButtons){
                btn.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));
                btn.setTextColor(ResourcesCompat.getColor(getResources(), R.color.buttonText, null));
            }
            for(Button btn: secondaryButtons){
                btn.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
                btn.setTextColor(ResourcesCompat.getColor(getResources(), R.color.buttonText, null));
            }
        }

        /*//Color Scheme changes
        String color = sp.getString("COLORSCHEME", "false");
        switch (color) {
            case "1":
                //getTheme().applyStyle(R.style.green, "");
                setTheme(R.style.AppTheme);
                mainAct.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
                break;
            case "2":
                setTheme(R.style.green);
                mainAct.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.purple_200, null));
                break;
            case "3":
                setTheme(R.style.violet);
                mainAct.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.black, null));
                break;
            default:
                break;
        }*/

    }

    @Override
    protected void onResume() {
        loadSettings();
        super.onResume();
    }
}