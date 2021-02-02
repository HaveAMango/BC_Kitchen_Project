package com.example.bc_kitchen_project.ui.login;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
class LoginFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer passwordConfirmError;

    private boolean isDataValid;

    public LoginFormState(@Nullable Integer usernameError, @Nullable Integer passwordError, @Nullable Integer passwordConfirmError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.passwordConfirmError = passwordConfirmError;
        this.isDataValid = false;
    }

    LoginFormState(@Nullable Integer usernameError, @Nullable Integer passwordError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }

    LoginFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    @Nullable
    Integer getPasswordConfirmError() {
        return passwordConfirmError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}