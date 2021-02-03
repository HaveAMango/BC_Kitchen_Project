package com.example.bc_kitchen_project.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.bc_kitchen_project.MainActivity;
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
public class LoginDataSourceImpl implements LoginDataSource {

    protected Map<String, String> userCache = new HashMap<>();

    private volatile static LoginDataSourceImpl instance;

    LoginDataSourceImpl() {
        initialize();
    }

    protected void initialize() {
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

                //try to login using user cache now
                MainActivity.onLoginHandled();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("login", "error: " + error);
            }
        });
    }

    public static LoginDataSourceImpl getInstance() {
        if (instance == null) {
            instance = new LoginDataSourceImpl();
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

        Log.i("login", "Login successful: " + username);
        LoggedInUser user = new LoggedInUser(username, username);
        return new Result.Success<>(user);
    }

    protected void addUser(String username, String password) {
        Log.i("register", "Register user: " + username);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference("users");
        DatabaseReference newUser = reference.push();
        newUser.child("name").setValue(username);
        newUser.child("password").setValue(password);
    }

    public Result<LoggedInUser> register(String username, String password) {
        if (userCache.containsKey(username)) {
            Log.e("login", "Username already taken:" + username);
            return new Result.Error(new LoginException(R.string.invalid_username));
        }

        addUser(username, password);

        Log.i("register", "Register successful: " + username);
        LoggedInUser user = new LoggedInUser(username, username);
        return new Result.Success<>(user);
    }
}