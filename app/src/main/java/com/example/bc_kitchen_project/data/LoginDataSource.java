package com.example.bc_kitchen_project.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.bc_kitchen_project.R;
import com.example.bc_kitchen_project.data.model.LoggedInUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private Map<String, String> userCache = new HashMap<>();

    private volatile static LoginDataSource instance;

    private LoginDataSource() {
        Log.i("login", "Init DataSource");
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference("users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //fill cache
                for (DataSnapshot user : snapshot.getChildren()) {
                    String username = user.child("name").getValue().toString();
                    String password = user.child("password").getValue().toString();

                    Log.d("login", "Added user: " + username + "-" + password);
                    userCache.put(username, password);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("login", "error: " + error);
            }
        });
    }

    public static LoginDataSource getInstance() {
        if (instance == null) {
            instance = new LoginDataSource();
        }

        return instance;
    }

    public Result<LoggedInUser> login(String username, String password) {
        if (!userCache.containsKey(username)) {
            Log.e("login", "User not found:" + username);
            return new Result.Error(new LoginException(R.string.login_failure_no_user));
        }

        String realPassword = userCache.get(username);
        if (!password.equals(realPassword)) {
            Log.e("login", "Invalid password for user:" + username +
                    " - Expected: " + realPassword + " actual: " + password);
            return new Result.Error(new LoginException(R.string.login_failure_invalid_password));
        }

        Log.i("login", "Login successfull: " + username);
        LoggedInUser user = new LoggedInUser(username, username);
        return new Result.Success<>(user);
    }

    public void logout() {
        // TODO: revoke authentication
    }
}