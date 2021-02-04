package com.example.bc_kitchen_project.settings;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.webkit.WebSettings;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.bc_kitchen_project.R;
import com.example.bc_kitchen_project.home.HomeActivity;

public class Settings extends PreferenceActivity {
    private static final String TAG = "Settingzzz";
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        intent = new Intent(this, Settings.class);
        loadSettings();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void loadSettings() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        boolean cNight = sp.getBoolean("NIGHT", false);
        if (cNight) {  //Night mode colors
            getListView().setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.backgroundNight, null));
            setTheme(R.style.SettingsFragmentStyle);
        } else {  //Disabled Night Mode colors
            getListView().setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        }

        CheckBoxPreference cNightInstant = (CheckBoxPreference) findPreference("NIGHT");
        cNightInstant.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                boolean yes = (boolean) o;
                if (yes) { //Night mode colors on press
                    getListView().setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.backgroundNight, null));
                    setTheme(R.style.SettingsFragmentStyle);
                    startActivity(intent);
                } else { //Disabled Night Mode colors on press
                    getListView().setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
                    setTheme(R.style.AppTheme);
                    startActivity(intent);
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        loadSettings();
        super.onResume();
    }
}
