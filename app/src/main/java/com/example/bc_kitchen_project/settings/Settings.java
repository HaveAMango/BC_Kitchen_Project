package com.example.bc_kitchen_project.settings;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
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
            getListView().setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
        } else {
            getListView().setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));
        }

        CheckBoxPreference cNightInstant = (CheckBoxPreference) findPreference("NIGHT");
        cNightInstant.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                boolean yes = (boolean) o;
                if (yes) {
                    getListView().setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));

                } else {
                    getListView().setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
                }
                return true;
            }
        });

        ListPreference lp = (ListPreference) findPreference("ORIENTATION");
        String orient = sp.getString("ORIENTATION", "false");
        if ("1".equals(orient)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
            lp.setSummary(lp.getEntry());
        } else if ("2".equals(orient)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            lp.setSummary(lp.getEntry());
        } else if ("3".equals(orient)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            lp.setSummary(lp.getEntry());
        }
        lp.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                String items = (String) o;
                if (preference.getKey().equals("ORIENTATION")) {
                    switch (items) {
                        case "1":
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
                            break;
                        case "2":
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                            break;
                        case "3":
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                            break;
                    }
                }
                return true;
            }
        });

        ListPreference textSize = (ListPreference) findPreference("TEXTSIZE");
        //FIXME next line can be used for changing text size (Maybe) or
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
        });

    }

    @Override
    protected void onResume() {
        loadSettings();
        super.onResume();
    }
}
