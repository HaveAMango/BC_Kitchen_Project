package com.example.bc_kitchen_project.data;

import android.content.Context;
import android.util.Log;

import com.example.bc_kitchen_project.MainActivity;
import com.example.bc_kitchen_project.data.model.LoggedInUser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
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

    private final boolean cacheSession;

    private boolean loadedCacheOnce;

    // private constructor : singleton access
    LoginRepository(boolean cacheSession, LoginDataSource dataSource) {
        this.cacheSession = cacheSession;
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance() {
        if (instance == null) {
            instance = new LoginRepository(true, LoginDataSourceImpl.getInstance());
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public static String activeUserId() {
        return getInstance().isLoggedIn() ? getInstance().user.getUserId() : null;
    }

    public void logout() {
        user = null;
        if (cacheSession) {
            removeUserCache();
        }
    }

    private File getCachedUserFile() {
        return new File(MainActivity.getContext().getFilesDir(), USER_CACHE_FILENAME);
    }

    public void loadCachedUser() {
        if (!cacheSession) return;

        if (loadedCacheOnce) return;

        loadedCacheOnce = true;

        Log.i("user-cache", "Load user cache");
        if (userCacheExists()) {
            Log.i("user-cache", "Load user cache, cache file exists - read");
            File cacheFile = getCachedUserFile();
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
        return getCachedUserFile().exists();
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
        if (userCacheExists()) {
            getCachedUserFile().delete();
        }
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<LoggedInUser> register(String username, String password) {
        Result<LoggedInUser> result = dataSource.register(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
            if (cacheSession) {
                storeUserCache(username, password);
            }
        }

        return result;
    }

    public Result<LoggedInUser> login(String username, String password) {
        // handle login
        Result<LoggedInUser> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
            if (cacheSession) {
                storeUserCache(username, password);
            }
        }

        return result;
    }
}