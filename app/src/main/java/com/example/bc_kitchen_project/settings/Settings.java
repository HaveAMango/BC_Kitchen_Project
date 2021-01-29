package com.example.bc_kitchen_project.settings;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.bc_kitchen_project.R;

public class Settings extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        loadSettings();
    }

    private void loadSettings() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        boolean cNight = sp.getBoolean("NIGHT", false);
        if (cNight) {
            //ResourcesCompat.getColor(getResources(), R.color.your_color, null)
            getListView().setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.purple_200, null));
            //Color.parseColor("@colors/purple_200")
        } else {
            getListView().setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.purple_700, null));


        }

        CheckBoxPreference cNightInstant = (CheckBoxPreference) findPreference("NIGHT");
        cNightInstant.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                boolean yes = (boolean) o;
                if (yes) {
                    getListView().setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.purple_200, null));

                } else {
                    getListView().setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.purple_700, null));

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
