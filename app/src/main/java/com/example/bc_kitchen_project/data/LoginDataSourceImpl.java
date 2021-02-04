package com.example.bc_kitchen_project.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.bc_kitchen_project.MainActivity;
import com.example.bc_kitchen_project.R;
import com.example.bc_kitchen_project.data.model.LoggedInUser;
import com.example.bc_kitchen_project.ui.login.Validation;
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

    private final String TAG_LOGIN = "Login";
    private final String TAG_REGISTER = "Register";

    private volatile static LoginDataSourceImpl instance;

    LoginDataSourceImpl() {
        initialize();
    }

    protected void initialize() {
        Log.i(TAG_LOGIN, "Init DataSource");
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i(TAG_LOGIN, "Users updated");

                //fill cache
                for (DataSnapshot user : snapshot.getChildren()) {
                    String username = (String) user.child("name").getValue();
                    String password = (String) user.child("password").getValue();

                    userCache.put(username, password);
                }

                //try to login using user cache now
                LoginRepository.getInstance().loadCachedUser();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG_LOGIN, "error: " + error);
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
            Log.e(TAG_LOGIN, "User not found:" + username);
            return new Result.Error(new LoginException(R.string.login_failure_no_user));
        }

        String realPassword = userCache.get(username);
        if (!password.equals(realPassword)) {
            Log.e(TAG_LOGIN, "Invalid password for user:" + username +
                    " - Expected: " + realPassword + " actual: " + password);
            return new Result.Error(new LoginException(R.string.login_failure_invalid_password));
        }

        Log.i(TAG_LOGIN, "Login successful: " + username);
        LoggedInUser user = new LoggedInUser(username, username);
        return new Result.Success<>(user);
    }

    protected void addUser(String username, String password) {
        Log.i(TAG_REGISTER, "Register user: " + username);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference("users");
        DatabaseReference newUser = reference.push();
        newUser.child("name").setValue(username);
        newUser.child("password").setValue(password);
    }

    public Result<LoggedInUser> register(String username, String password, String confirmPassword) {
        //invalid username
        Integer usernameError = Validation.validateUsername(username);
        if (usernameError != null) {
            Log.e(TAG_REGISTER, "Invalid username: " + username);
            return new Result.Error(new LoginException(usernameError));
        }

        if (userCache.containsKey(username)) {
            Log.e(TAG_REGISTER, "Username already taken: " + username);
            return new Result.Error(new LoginException(R.string.username_taken));
        }

        Integer passwordError = Validation.validatePassword(password);
        if (passwordError != null) {
            Log.e(TAG_REGISTER, "Invalid password: " + password);
            return new Result.Error(new LoginException(passwordError));
        }

        if (!password.equals(confirmPassword)) {
            Log.e(TAG_REGISTER, "Passwords do not match for: " + username);
            return new Result.Error(new LoginException(R.string.password_no_match));
        }

        addUser(username, password);

        Log.i(TAG_REGISTER, "Register successful: " + username);
        LoggedInUser user = new LoggedInUser(username, username);
        return new Result.Success<>(user);
    }
}