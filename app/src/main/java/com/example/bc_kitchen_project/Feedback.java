package com.example.bc_kitchen_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bc_kitchen_project.home.HomeActivity;

public class Feedback extends AppCompatActivity {

    TextView feedback_rateThis;
    RatingBar ratingBar;
    EditText rating_name, rating_comment;
    CheckBox rating_checkBox;
    Button btn_rating_cancel, btn_rating_submit;
    TextView email_send;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feedback_rateThis= findViewById(R.id.feedback_rateThis);
        rating_name = findViewById(R.id.rating_name);
        rating_comment = findViewById(R.id.rating_comment);
        rating_checkBox = findViewById(R.id.rating_checkBox);
        btn_rating_cancel = findViewById(R.id.btn_rating_cancel);
        btn_rating_submit = findViewById(R.id.btn_rating_submit);
        email_send = findViewById(R.id.email_send);

        ratingBar = findViewById(R.id.ratingBar);
        final float[] ratingValue = new float[1];
        ratingBar.setNumStars(5);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingValue[0] = ratingBar.getRating();
            }
        });

        btn_rating_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = rating_name.getText().toString().trim();
                String comment = rating_comment.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    rating_name.setError("Name field can not be empty!");
                    return;
                }
                if (!rating_checkBox.isChecked()) {
                    rating_checkBox.setError("You have to agree that your comment may be seen by other users.");
                    return;
                }

                if (ratingValue[0] <=0) {
                    Toast.makeText(Feedback.this, "Check the rating value", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(Feedback.this, "name: " + name + ", comment: " + comment + ", rating: " + ratingValue[0], Toast.LENGTH_LONG).show();
            }
        });

        email_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Feedback.this, "There will be Dialog window with options to send email", Toast.LENGTH_SHORT).show();
                openDialog();
            }
        });

        btn_rating_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });
    }

    public void openDialog() {
        SendEmail sendEmail = new SendEmail();
        sendEmail.show(getSupportFragmentManager(), "Choose Email message type");
    }


}