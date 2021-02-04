package com.example.bc_kitchen_project.data;

import com.example.bc_kitchen_project.R;
import com.example.bc_kitchen_project.data.model.LoggedInUser;

import org.junit.Before;
import org.junit.Test;

public class LoginRepositoryTest {

    private LoginRepository repository;
    private MockDataSource dataSource;

    @Before
    public void setUp() {
        dataSource = new MockDataSource();
        repository = new LoginRepository(false, dataSource);

        dataSource.addUser("admin", "qwerty");
    }

    @Test
    public void testLoginSuccess() {
        Result<LoggedInUser> result = repository.login("admin", "qwerty");
        assert result instanceof Result.Success;

        Result.Success<LoggedInUser> success = (Result.Success<LoggedInUser>) result;
        assert success.getData().getDisplayName().equals("admin");
    }

    @Test
    public void testLoginFailureNoUser() {
        Result<LoggedInUser> result = repository.login("wrong", "qwerty");
        assert result instanceof Result.Error;

        Result.Error<LoginException> error = (Result.Error<LoginException>) result;
        assert error.getError().getErrorCode() == R.string.login_failure_no_user;
    }

    @Test
    public void testLoginFailureInvalidPassword() {
        Result<LoggedInUser> result = repository.login("admin", "wrong");
        assert result instanceof Result.Error;

        Result.Error<LoginException> error = (Result.Error<LoginException>) result;
        assert error.getError().getErrorCode() == R.string.login_failure_invalid_password;
    }

    @Test
    public void testRegisterSuccess() {
        assert dataSource.size() == 1;
        Result<LoggedInUser> result = repository.register("user", "qwerty", "qwerty");
        assert result instanceof Result.Success;

        Result.Success<LoggedInUser> success = (Result.Success<LoggedInUser>) result;
        assert success.getData().getDisplayName().equals("user");

        assert dataSource.size() == 2;
    }

    @Test
    public void testRegisterFailureUserExists() {
        assert dataSource.size() == 1;
        Result<LoggedInUser> result = repository.register("admin", "qwerty", "qwerty");
        assert result instanceof Result.Error;

        Result.Error<LoginException> error = (Result.Error<LoginException>) result;
        assert error.getError().getErrorCode() == R.string.username_taken;

        assert dataSource.size() == 1;
    }

    @Test
    public void testRegisterFailureConfirmPassword() {
        assert dataSource.size() == 1;
        Result<LoggedInUser> result = repository.register("user", "qwerty", "wrong");
        assert result instanceof Result.Error;

        Result.Error<LoginException> error = (Result.Error<LoginException>) result;
        assert error.getError().getErrorCode() == R.string.password_no_match;

        assert dataSource.size() == 1;
    }
}

class MockDataSource extends LoginDataSourceImpl {

    public void addUser(String username, String password) {
        userCache.put(username, password);
    }

    public int size() {
        return userCache.size();
    }

    @Override
    protected void initialize() {
        //Don't initialize Firebase
    }
}
