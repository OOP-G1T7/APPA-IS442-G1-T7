package com.example.analyticsapp.user.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class PasswordValidationTest {
    @Test
    public void testValidatePasswordValid() {
        assertDoesNotThrow(() -> {
            PasswordValidation.validatePassword("Av1SF%ADFASDFASDF", "Av1SF%ADFASDFASDF");
        });
    }

    @Test
    public void testValidatePasswordDifferentPassword() {
        assertThrows(InvalidPasswordException.class, () -> {
            PasswordValidation.validatePassword("Av1SF%ADFASDFASD", "Av1SF%ADFASDFASDF");
        });
    }

    @Test
    public void testValidatePasswordNoLowerCase() {
        assertThrows(InvalidPasswordException.class, () -> {
            PasswordValidation.validatePassword("A1SF%ADFASDFASDF", "A1SF%ADFASDFASDF");
        });
    }

    @Test
    public void testValidatePasswordNoUpperCase() {
        assertThrows(InvalidPasswordException.class, () -> {
            PasswordValidation.validatePassword("das1asdf%asdf", "das1asdf%asdf");
        });
    }

    @Test
    public void testValidatePasswordNoSymbol() {
        assertThrows(InvalidPasswordException.class, () -> {
            PasswordValidation.validatePassword("das1asdfasdf", "das1asdfasdf");
        });
    }

    @Test
    public void testValidatePasswordTooShort() {
        assertThrows(InvalidPasswordException.class, () -> {
            PasswordValidation.validatePassword("1%aA", "1%aA");
        });
    }

    @Test
    public void testValidatePasswordTooLong() {
        assertThrows(InvalidPasswordException.class, () -> {
            PasswordValidation.validatePassword("1%aAasdfghjklasdfghjklasdfghjkl", "1%aAasdfghjklasdfghjklasdfghjkl");
        });
    }

}