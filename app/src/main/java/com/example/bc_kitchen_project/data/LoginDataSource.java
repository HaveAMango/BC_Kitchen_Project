package com.example.bc_kitchen_project.data;

import com.example.bc_kitchen_project.data.model.LoggedInUser;

public interface LoginDataSource {

    Result<LoggedInUser> register(String username, String password);

    Result<LoggedInUser> login(String username, String password);
}
