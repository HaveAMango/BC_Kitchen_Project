package com.example.bc_kitchen_project.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.example.bc_kitchen_project.data.LoginException;
import com.example.bc_kitchen_project.data.LoginRepository;
import com.example.bc_kitchen_project.data.Result;
import com.example.bc_kitchen_project.data.model.LoggedInUser;
import com.example.bc_kitchen_project.R;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        Result<LoggedInUser> result = loginRepository.login(username, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            Result.Error errorResult = (Result.Error) result;
            LoginException loginException = (LoginException) errorResult.getError();
            loginResult.setValue(new LoginResult(loginException.getErrorCode()));
        }
    }

    public void register(String username, String password) {
        Result<LoggedInUser> result = loginRepository.register(username, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            Result.Error errorResult = (Result.Error) result;
            LoginException loginException = (LoginException) errorResult.getError();
            loginResult.setValue(new LoginResult(loginException.getErrorCode()));
        }
    }

    public void registerDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    public void registerDataChanged(String username, String password, String confirmPassword) {
        Integer usernameError = null;
        Integer passError = null;
        Integer passConfirmError = null;

        if (!isUserNameValid(username)) {
            usernameError = R.string.invalid_username;
        }

        if (!isPasswordValid(password)) {
            passError = R.string.invalid_password;
        }

        if (!passwordsMatch(password, confirmPassword)) {
            passConfirmError = R.string.password_no_match;
        }

        if (usernameError == null && passError == null && passConfirmError == null) {
            loginFormState.setValue(new LoginFormState(true));
        } else {
            loginFormState.setValue(new LoginFormState(usernameError, passError, passConfirmError));
        }
    }

    private boolean passwordsMatch(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 3;
    }
}