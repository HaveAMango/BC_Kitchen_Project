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

import com.example.bc_kitchen_project.data.LoginRepository;
import com.example.bc_kitchen_project.home.HomeActivity;
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
import java.util.Locale;

import static android.R.layout.simple_list_item_1;

public class Fridge extends AppCompatActivity {
    private DatabaseReference database;
    private ArrayList<String> productsList = new ArrayList<>();
    ListView theListView;
    @Override
    public void onBackPressed() { //makes sure, that is user presses "back" from Fridge, goes to main
        Intent intent = new Intent(Fridge.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);
        setTitle("Fridge");
        theListView = (ListView) findViewById(R.id.myListView);
        database = FirebaseDatabase.getInstance().getReference("user-fridge").child(LoginRepository.activeUserId());
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productsFromDatabaseToList(snapshot);
                ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Fridge.this,
                        simple_list_item_1,
                        productsList);
                theListView.setAdapter(myAdapter); //adds the products from DB to ListView
                theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //what happens if user clicks on a product
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        String product = productsList.get(position).split(", ")[0].trim();
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() { //a choice is offered to take one product out
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE: //if says yes then
                                        database = FirebaseDatabase.getInstance().getReference("user-fridge").child(LoginRepository.activeUserId()).child(product);
                                        positiveClick();
                                        dialog.cancel();
                                        askIfAddToGroceryList(product);
                                        break;
                                    case DialogInterface.BUTTON_NEGATIVE: //if says no, nothing happens
                                        break;
                                }
                            }
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(Fridge.this);
                        builder.setMessage("Do you want to take one " + product + " out of the fridge?").setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener).show(); //the offer

                    }

                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("products", "error: " + error);
            }
        });
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() { //goes to insert product page
            public void onClick(View v) {
                Intent intent = new Intent(Fridge.this, InsertProductFridge.class);
                startActivity(intent);
            }
        });
    }

    private void productsFromDatabaseToList(DataSnapshot snapshot) {
        for (DataSnapshot product : snapshot.getChildren()) {
            String name = product.child("name").getValue().toString();
            Integer year = Integer.parseInt(product.child("date").child("year").getValue().toString());
            Integer month = Integer.parseInt(product.child("date").child("month").getValue().toString());
            Integer day = Integer.parseInt(product.child("date").child("date").getValue().toString());
            Integer count = Integer.parseInt(product.child("count").getValue().toString()); //get products from database
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String dateString;
            String old = "";
            if (year == 5000) {
                productsList.add(name + ",  Count: " + count + " "); //if date wasn't set, automatically it's 5000
            } else {
                Date date = new Date(year - 1900, month, day); //date formating
                dateString = df.format(date);
                boolean is = (date).after(new Date()); //checks the date
                if (!is) {  //warning if old
                    old = "BE CAREFUL, IT'S OLD";
                    productsList.add(name + ",  Count: " + count + " " + old);
                } else
                    productsList.add(name + ",  Expires: " + dateString + " Count: " + count + " ");
            }
        }
    }

    private void positiveClick() {
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int countCurrent = Integer.parseInt(snapshot.child("count").getValue().toString());
                removeOneItem(countCurrent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("products", "error: " + error);
            }
        });
    }

    private void removeOneItem(int countCurrent) {
        if (countCurrent == 1) { //if one product left, deletes it from DB
            database.removeValue();
        } else { //if more than one left, rewrites the count to one less
            countCurrent--;
            database.child("count").setValue(countCurrent);
        }
    }

    public void askIfAddToGroceryList(String product){
        DialogInterface.OnClickListener dialogClickListener2 = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        database = FirebaseDatabase.getInstance().getReference();
                        Product temp = new Product(product);
                        writeNewProduct(temp);
                        finish(); //reloads the page to always get active changes
                        startActivity(getIntent());
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        finish(); //reloads the page to always get active changes
                        startActivity(getIntent());
                        break;
                }
            }
        };

        AlertDialog.Builder builder2 = new AlertDialog.Builder(Fridge.this);
        builder2.setMessage("Do you want to add this product to Grocery List?").setPositiveButton("Yes", dialogClickListener2)
                .setNegativeButton("No", dialogClickListener2).show();
    }
    private void writeNewProduct(Product product) { //writes product to database
        database.child("user-groceries").child(LoginRepository.activeUserId()).child(product.name).setValue(product);
    }

}