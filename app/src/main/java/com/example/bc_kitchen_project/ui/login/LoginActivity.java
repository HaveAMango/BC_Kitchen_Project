package com.example.bc_kitchen_project.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bc_kitchen_project.R;
import com.example.bc_kitchen_project.home.HomeActivity;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;
    ConstraintLayout loginAct;
    EditText usernameEditText;
    EditText passwordEditText;
    Button loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        loginAct = findViewById(R.id.container);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        loadSettings();
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);

                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }

                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());

                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);

                    setResult(Activity.RESULT_OK);
                    //Complete and destroy login activity once successful
                    finish();
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    //Loads Settings
    private void loadSettings() {
        //Color Schemes
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean cNight = sp.getBoolean("NIGHT", false);
        //What colors in Night Mode
        if (cNight) {
            loginAct.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.backgroundNight, null));
            usernameEditText.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
            passwordEditText.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
            usernameEditText.setHintTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
            passwordEditText.setHintTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
            loginButton.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.secondaryButtonNight, null));
            loginButton.setTextColor(ResourcesCompat.getColor(getResources(), R.color.buttonTextNight, null));
        }
    }

    @Override
    protected void onResume() {
        loadSettings();
        super.onResume();
    }
}