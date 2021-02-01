package com.example.bc_kitchen_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bc_kitchen_project.ui.login.LoginActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.R.layout.activity_list_item;
import static android.R.layout.simple_list_item_1;

public class Products extends AppCompatActivity {
    private DatabaseReference database;
    private ArrayList<String> productsList = new ArrayList<>();

    ListView theListView;
    Intent myIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        setTitle("Products");
        theListView = (ListView) findViewById(R.id.myListView);
        database = FirebaseDatabase.getInstance().getReference("products");
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot product : snapshot.getChildren()) {
                    String name = product.child("name").getValue().toString();
                    productsList.add(name);

                    Log.d("products", "Added product: " + name);
                }

                ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Products.this,
                        simple_list_item_1,
                        productsList);
                theListView.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("products", "error: " + error);
            }
        });
    }

    private void writeNewProduct(String name) {
        database.child("products").child("3").setValue(name);
    }
}


