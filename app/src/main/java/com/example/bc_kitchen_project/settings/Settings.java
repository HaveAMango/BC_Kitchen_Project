package com.example.bc_kitchen_project.settings;

import android.app.Activity;
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

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.bc_kitchen_project.R;

public class Settings extends PreferenceActivity {
    private static final String TAG = "Settingzzz";


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
            getListView().setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.backgroundNight, null));
            setTheme(R.style.green);
            //TODO change text color to white
        } else {
            getListView().setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        }

        CheckBoxPreference cNightInstant = (CheckBoxPreference) findPreference("NIGHT");
        cNightInstant.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                boolean yes = (boolean) o;
                if (yes) {
                    getListView().setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.backgroundNight, null));
                    //TODO change text color to white
                } else {
                    getListView().setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
                }
                return true;
            }
        });


        /*ListPreference lp = (ListPreference) findPreference("COLORSCHEME");
        String color = sp.getString("COLORSCHEME", "false");
        switch (color) {
            case "1":
                mainAct.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
                break;
            case "2":
                mainAct.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorFloatingButton, null));
                break;
            case "3":
                mainAct.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.purple_200, null));
                break;
            default:
                break;
        }
        switch (color) {
            case "1":
                setTheme(R.style.AppTheme);
                //BackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
                //lp.setSummary(lp.getEntry());
                break;
            case "2":
                setTheme(R.style.green);
                //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                //lp.setSummary(lp.getEntry());
                break;
            case "3":
                setTheme(R.style.violet);
                //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                //lp.setSummary(lp.getEntry());
                break;
            default:
                break;
        }

        lp.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                String items = (String) o;
                if (preference.getKey().equals("COLORSCHEME")) {
                    switch (items) {
                        case "1":
                            setTheme(R.style.AppTheme);
                            break;
                        case "2":
                            setTheme(R.style.green);
                            break;
                        case "3":
                            setTheme(R.style.darkTheme);
                            break;
                        default:
                            break;
                    }
                }
                return true;
            }
        });*/

        /*ListPreference textSize = (ListPreference) findPreference("TEXTSIZE");
        // Next line can be used for changing text size (Maybe) or
        // line1Size = a.getDimensionPixelSize(R.styleable.StackedTextView_line1_textSize, 0);
        // tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.txt_size));
        String txtSize = sp.getString("TEXTSIZE", "false");
        if ("1".equals(txtSize)) {
            String value = String.valueOf(getResources().getDimensionPixelSize(R.dimen.font_size1));
            Log.d(TAG, "Value:   " + value);
            //textSize.setValue((String)TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.font_size1));
            textSize.setSummary(textSize.getEntry());
        } else if ("2".equals(txtSize)) {
            String value = String.valueOf(getResources().getDimensionPixelSize(R.dimen.font_size2));
            Log.d(TAG, "Value:   " + value);
            textSize.setSummary(textSize.getEntry());
        } else if ("3".equals(txtSize)) {
            String value = String.valueOf(getResources().getDimensionPixelSize(R.dimen.font_size3));
            Log.d(TAG, "Value:   " + value);
            textSize.setSummary(textSize.getEntry());
        }
        textSize.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            String value = "";

            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                String items = (String) o;
                if (preference.getKey().equals("TEXTSIZE")) {
                    switch (items) {
                        case "1":
                            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
                            value = String.valueOf(getResources().getDimensionPixelSize(R.dimen.font_size1));
                            Log.d(TAG, "ValueOnChange1:   " + value);
                            break;
                        case "2":
                            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                            value = String.valueOf(getResources().getDimensionPixelSize(R.dimen.font_size2));
                            Log.d(TAG, "ValueOnChange2:   " + value);
                            break;
                        case "3":
                            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                            value = String.valueOf(getResources().getDimensionPixelSize(R.dimen.font_size3));
                            Log.d(TAG, "ValueOnChange3:   " + value);
                            break;
                    }
                }
                Log.d(TAG, "End value: " + value);
                return true;
            }
        });*/

    }

    @Override
    protected void onResume() {
        loadSettings();
        super.onResume();
    }
}
