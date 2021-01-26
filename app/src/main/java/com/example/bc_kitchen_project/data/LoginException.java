package com.example.bc_kitchen_project.data;

public class LoginException extends Exception {

    private int errorCode;

    public LoginException(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
