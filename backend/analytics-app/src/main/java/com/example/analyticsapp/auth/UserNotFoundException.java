package com.example.analyticsapp.auth;

/**
 * A custom exception class for handling invalid passwords.
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String msg) {
        super(msg);
    }
}
