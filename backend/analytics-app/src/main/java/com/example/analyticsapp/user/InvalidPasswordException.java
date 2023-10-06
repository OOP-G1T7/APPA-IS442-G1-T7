package com.example.analyticsapp.user;

/**
 * A custom exception class for handling invalid passwords.
 */
public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException(String msg) {
        super(msg);
    }
}
