package com.example.bc_kitchen_project.data;

import android.content.Context;
import android.util.Log;

import com.example.bc_kitchen_project.MainActivity;
import com.example.bc_kitchen_project.data.model.LoggedInUser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static final String USER_CACHE_FILENAME = "user.cache";

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance() {
        if (instance == null) {
            instance = new LoginRepository(LoginDataSource.getInstance());
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    public void loadCachedUser() {
        Log.i("user-cache", "Load user cache");
        if (userCacheExists()) {
            Log.i("user-cache", "Load user cache, cache file exists - read");
            File cacheFile = new File(MainActivity.getContext().getFilesDir(), USER_CACHE_FILENAME);
            try {
                String storedCredentials = new BufferedReader(new FileReader(cacheFile)).readLine();
                Log.i("user-cache","Read user from cache: " + storedCredentials);

                String[] parts = storedCredentials.split(":");
                String username = parts[0];
                String password = parts[1];
                login(username, password);
            } catch (IOException e) {
                Log.e("user-cache","Error while reading cached user: " + e.getMessage());
            }
        }
    }

    private boolean userCacheExists() {
        File cacheFile = new File(MainActivity.getContext().getFilesDir(), USER_CACHE_FILENAME);
        return cacheFile.exists();
    }

    private void storeUserCache(String username, String password) {
        try {
            Log.i("user-cache", "Store user cache");
            try (FileOutputStream fos = MainActivity.getContext().openFileOutput(USER_CACHE_FILENAME, Context.MODE_PRIVATE)) {
                String contents = username + ":" + password;
                fos.write(contents.getBytes());
            }
        } catch (IOException e) {
            Log.e("user-cache","Error while storing cached user: " + e.getMessage());
        }
        //store "username:password" in cache file named "user.cache"
    }

    private void removeUserCache() {


    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<LoggedInUser> login(String username, String password) {
        // handle login
        Result<LoggedInUser> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
            storeUserCache(username, password);
        }

        return result;
    }
}