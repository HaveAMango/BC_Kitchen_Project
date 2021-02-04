package com.example.bc_kitchen_project.ui.login;

import androidx.annotation.Nullable;

import com.example.bc_kitchen_project.R;

import java.util.regex.Pattern;

public class Validation {

    private static final String ALPHANUM_REGEX = "^[a-zA-Z0-9]+$";
    private static final Pattern ALPHANUMERIC = Pattern.compile(ALPHANUM_REGEX);

    @Nullable
    public static Integer validateUsername(String username) {
        if (username == null) {
            return R.string.invalid_username;
        }

        if (!ALPHANUMERIC.matcher(username).matches()) {
            return R.string.invalid_username;
        }

        if (username.trim().length() < 4) {
            return R.string.invalid_username;
        }

        return null;
    }

    @Nullable
    public static Integer validatePassword(String password) {
        if (password == null || password.length() <= 3) {
            return R.string.invalid_password;
        }

        return null;
    }
}
