package com.example.bc_kitchen_project.findRecipe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bc_kitchen_project.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FindRecipe extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "FindRecipe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_recipe);
        Log.d(TAG, "workening");
        Button findRecButton = (Button)findViewById(R.id.findRecipeButton);
        findRecButton.setOnClickListener(this);

    }

    void connect(){
        Uri uriUrl = Uri.parse("http://google.com/");
        //Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        Intent search = new Intent(Intent.ACTION_WEB_SEARCH);
        search.putExtra(SearchManager.QUERY, "eggs");
        startActivity(search);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this, "Finde Recipe clicked", Toast.LENGTH_LONG).show();
        connect();

    }
}