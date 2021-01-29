package com.example.bc_kitchen_project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InsertProductPantry extends AppCompatActivity {
    private DatabaseReference database;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(InsertProductPantry.this, Pantry.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_product_pantry);
        setTitle("Add product in pantry");
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText name = (EditText) findViewById(R.id.editTextTextPersonName);
                if (TextUtils.isEmpty(name.getText().toString().trim())) {
                    name.setError("Please enter a name");
                } else {
                    EditText count = (EditText) findViewById(R.id.editTextNumberSigned);
                    if (TextUtils.isEmpty(count.getText().toString().trim())) {
                        count.setError("Please enter count");
                    } else {
                        EditText day = (EditText) findViewById(R.id.editTextNumberSigned2);
                        EditText month = (EditText) findViewById(R.id.editTextNumberSigned3);
                        EditText year = (EditText) findViewById(R.id.editTextNumberSigned4);
                        if ((TextUtils.isEmpty(day.getText().toString().trim())) ||
                                (TextUtils.isEmpty(month.getText().toString().trim())) ||
                                (TextUtils.isEmpty(year.getText().toString().trim()))) {
                            database = FirebaseDatabase.getInstance().getReference();
                            Product product = new Product(name.getText().toString().trim(), Integer.parseInt(count.getText().toString().trim()));
                            writeNewProduct(product);
                        } else {
                            database = FirebaseDatabase.getInstance().getReference();
                            Product product = new Product(name.getText().toString().trim(),
                                    Integer.parseInt(count.getText().toString().trim()),
                                    Integer.parseInt(year.getText().toString().trim()),
                                    Integer.parseInt(month.getText().toString().trim()),
                                    Integer.parseInt(day.getText().toString().trim()));
                            writeNewProduct(product);
                        }
                        Toast.makeText(InsertProductPantry.this, "Added",
                                Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(getIntent());
                    }
                }
            }
        });
    }

    private void writeNewProduct(Product product) {
        database.child("user-pantry").child(Pantry.activeUserId).child(product.name).setValue(product);
    }
}