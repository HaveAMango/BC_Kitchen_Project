package com.example.bc_kitchen_project;

import com.google.firebase.database.IgnoreExtraProperties;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@IgnoreExtraProperties
public class Product {
    public String name;
    public Integer count;
    public Date date;

    public Product() {
    }

    public Product(String name, Integer count, Integer year, Integer month, Integer day) { //this is standart insert product form
        this.count = count;
        this.name = name;
        this.date = new Date(year, month-1, day);


    }

    public Product (String name){
        this.name = name;
    } //this is used in Grocery List

    public Product(String name, Integer count) { //if date is not set by user
        this.count = count;
        this.name = name;
        this.date = new Date(5000,1,1);
    }
}
