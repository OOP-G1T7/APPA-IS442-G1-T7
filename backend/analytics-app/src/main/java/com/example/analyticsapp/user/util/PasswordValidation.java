package com.example.analyticsapp.user.util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for validating passwords during registration.
 */
public class PasswordValidation {
    public static void validatePassword(String password, String passwordConfirm) {
        ArrayList<String> errors = new ArrayList<String>();
        if (password.length() < 8) {
            errors.add("Password too short");
        }
        if (password.length() > 25) {
            errors.add("Password too long");
        }
        if (!containsUpperCase(password)) {
            errors.add("Password requires an upper case");
        }
        if (!containsLowerCase(password)) {
            errors.add("Password requires a lower case");
        }
        if (!containsDigit(password)) {
            errors.add("Password requires a number");
        }
        if (!containsSymbol(password)) {
            errors.add("Password requires a symbol");
        }
        if (!password.equals(passwordConfirm)) {
            errors.add("Confirmation password does not match the first password");
        }
        if (errors.size() != 0) {
            throw new InvalidPasswordException(errors.toString());
        }
    }

    public static boolean containsUpperCase(String input) {
        for (char c : input.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true; // Found an uppercase character
            }
        }
        return false; // No uppercase characters found
    }

    public static boolean containsLowerCase(String input) {
        for (char c : input.toCharArray()) {
            if (Character.isLowerCase(c)) {
                return true; // Found a lower character
            }
        }
        return false; // No lowercase characters found
    }

    public static boolean containsDigit(String input) {
        for (char c : input.toCharArray()) {
            if (Character.isDigit(c)) {
                return true; // Found a digit
            }
        }
        return false; // No digits found
    }

    public static boolean containsSymbol(String input) {
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(input);
        return matcher.find(); // Returns true if at least one symbol is found
    }
}
