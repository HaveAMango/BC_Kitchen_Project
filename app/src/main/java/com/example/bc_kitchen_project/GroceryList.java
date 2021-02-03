package com.example.bc_kitchen_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bc_kitchen_project.data.LoginRepository;
import com.example.bc_kitchen_project.ui.login.LoginActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//import kotlin.sequences.ConstrainedOnceSequence;

import static android.R.layout.simple_list_item_1;
import static android.R.layout.simple_list_item_checked;
import static android.R.layout.simple_list_item_multiple_choice;
import static android.R.layout.test_list_item;

public class GroceryList extends AppCompatActivity {

    private DatabaseReference database;
    private ArrayList<String> productsList = new ArrayList<>();
    ListView theListView;
    ConstraintLayout groceryAct;
    Button btn1, btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Grocery List");

        setContentView(R.layout.activity_grocery_list);
        theListView = (ListView) findViewById(R.id.myListView);
        groceryAct = findViewById(R.id.groceryListActivity);
        btn1 = findViewById(R.id.button3);
        btn2 = findViewById(R.id.button4);
        loadSettings();
        database = FirebaseDatabase.getInstance().getReference("user-groceries").child(LoginRepository.activeUserId());
        database.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot product : snapshot.getChildren()) {
                    String name = product.child("name").getValue().toString(); //gets all products from DB
                    productsList.add(name);
                }
                ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(GroceryList.this,
                        simple_list_item_multiple_choice,
                        productsList); //makes the listView with multiple choice checkboxes
                theListView.setAdapter(myAdapter);

                theListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("products", "error: " + error);
            }
        });

        Button button = findViewById(R.id.button3); //if person presses ADD button, what happens
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText name = (EditText) findViewById(R.id.editTextTextPersonName4);
                String productEntered = name.getText().toString().trim();
                if (TextUtils.isEmpty(productEntered)) {
                    name.setError("Please enter a product"); //can't have an empty field
                } else {
                    Product temp = new Product(productEntered);
                    database = FirebaseDatabase.getInstance().getReference();
                    writeNewProduct(temp); //puts product in database
                    finish(); //reloads page, so would be always up to date
                    startActivity(getIntent());
                }
            }
        });
        Button button2 = findViewById(R.id.button4); //finds all checked boxes and deletes those products from database
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < theListView.getCount(); i++) {
                    if (theListView.isItemChecked(i)) {
                        DatabaseReference temp = FirebaseDatabase.getInstance().getReference("user-groceries").child(LoginRepository.activeUserId()).child(productsList.get(i));
                        temp.removeValue();
                    }
                }
                finish();
                startActivity(getIntent());

            }
        });
    }

    private void writeNewProduct(Product product) { //writes product to database
        database.child("user-groceries").child(LoginRepository.activeUserId()).child(product.name).setValue(product);
    }

    //Loads Settings
    private void loadSettings() {
        //Color Schemes
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean cNight = sp.getBoolean("NIGHT", false);
        //What colors in Night Mode
        if (cNight) {
            groceryAct.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.backgroundNight, null));
            btn1.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.backgroundNight, null));
            btn2.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.backgroundNight, null));
        }
        //What colors when Night Mode is off
        else {
            groceryAct.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
            btn1.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));
            btn2.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
        }
    }

    @Override
    protected void onResume() {
        loadSettings();
        super.onResume();
    }
}