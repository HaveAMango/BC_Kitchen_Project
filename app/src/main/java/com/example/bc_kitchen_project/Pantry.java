package com.example.bc_kitchen_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.R.layout.simple_list_item_1;

public class Pantry extends AppCompatActivity {
    private DatabaseReference database;
    private ArrayList<String> productsList = new ArrayList<>();
    public static String activeUserId = "0";
    ListView theListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);
        setTitle("Pantry");
        theListView = (ListView) findViewById(R.id.myListView);
        database = FirebaseDatabase.getInstance().getReference("user-pantry").child(activeUserId);
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot product : snapshot.getChildren()) {
                    String name = product.child("name").getValue().toString();
                    Integer year = Integer.parseInt(product.child("date").child("year").getValue().toString());
                    Integer month = Integer.parseInt(product.child("date").child("month").getValue().toString());
                    Integer day = Integer.parseInt(product.child("date").child("day").getValue().toString());
                    Integer count = Integer.parseInt(product.child("count").getValue().toString());
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String dateString;
                    String old = "";
                    if (year == 5000) {
                        productsList.add(name + ",  Count: " + count + " ");
                    } else {
                        Date date = new Date(year - 1900, month, day);
                        dateString = df.format(date);
                        boolean is = (date).after(new Date());
                        if (!is) {
                            old = "BE CAREFUL, IT'S OLD";
                            productsList.add(name + ",  Count: " + count + " " + old);
                        } else
                            productsList.add(name + ",  Expires: " + dateString + " Count: " + count + " ");
                    }
                }
                ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Pantry.this,
                        simple_list_item_1,
                        productsList);
                theListView.setAdapter(myAdapter);
                theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        String product = productsList.get(position).split(", ")[0].trim();
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        database = FirebaseDatabase.getInstance().getReference("user-pantry").child(activeUserId).child(product);
                                        database.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                int countCurrent = Integer.parseInt(snapshot.child("count").getValue().toString());
                                                if (countCurrent == 1) {
                                                    DatabaseReference temp = FirebaseDatabase.getInstance().getReference("user-pantry").child(activeUserId).child(product);
                                                    database.removeValue();
                                                } else {
                                                    countCurrent--;
                                                    database.child("count").setValue(countCurrent);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                Log.e("products", "error: " + error);
                                            }
                                        });
                                        finish();
                                        startActivity(getIntent());
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        break;
                                }
                            }
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(Pantry.this);
                        builder.setMessage("Do you want to take one " + product + " out of the fridge?").setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener).show();


                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("products", "error: " + error);
            }
        });
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Pantry.this, InsertProductPantry.class);
                startActivity(intent);
            }
        });
    }
}