package com.example.bc_kitchen_project.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.bc_kitchen_project.data.model.LoggedInUser;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public LoginDataSource() {
        Log.i("login", "Init DataSource");
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference("users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("login", "snapshot: " + snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("login", "error: " + error);
            }
        });
    }

    public Result<LoggedInUser> login(String username, String password) {

        try {

            // TODO: handle loggedInUser authentication
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe");
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}