package com.example.analyticsapp.user.util;

/**
 * A custom exception class for handling invalid passwords.
 */
public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException(String msg) {
        super(msg);
    }
}
