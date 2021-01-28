package com.example.bc_kitchen_project.data;

import android.content.Context;

import com.example.bc_kitchen_project.MainActivity;
import com.example.bc_kitchen_project.data.model.LoggedInUser;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
        loadUserCache();
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

    private void loadUserCache() {
        MainActivity.getContext().getCacheDir();
    }

    private void storeUserCache() {
        //store "username:password" in cache file named "user.cache"
    }

    private void removeUserCache() {


    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        storeUserCache();
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<LoggedInUser> login(String username, String password) {
        // handle login
        Result<LoggedInUser> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }

        return result;
    }
}