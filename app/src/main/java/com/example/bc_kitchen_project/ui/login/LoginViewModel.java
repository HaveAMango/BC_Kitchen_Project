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

    public void register(String username, String password, String confirmPassword) {
        Result<LoggedInUser> result = loginRepository.register(username, password, confirmPassword);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            Result.Error errorResult = (Result.Error) result;
            LoginException loginException = (LoginException) errorResult.getError();
            loginResult.setValue(new LoginResult(loginException.getErrorCode()));
        }
    }

    public void loginDataChanged(String username, String password) {
        loginFormState.setValue(new LoginFormState(true));
    }

    public void registerDataChanged(String username, String password, String confirmPassword) {
        Integer usernameError = Validation.validateUsername(username);
        Integer passError = Validation.validatePassword(password);
        Integer passConfirmError = password.equals(confirmPassword) ? null : R.string.password_no_match;

        if (usernameError == null && passError == null && passConfirmError == null) {
            loginFormState.setValue(new LoginFormState(true));
        } else {
            loginFormState.setValue(new LoginFormState(usernameError, passError, passConfirmError));
        }
    }
}