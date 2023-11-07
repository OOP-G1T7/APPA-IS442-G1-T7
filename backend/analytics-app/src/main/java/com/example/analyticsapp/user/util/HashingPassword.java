package com.example.analyticsapp.user.util;

import org.mindrot.jbcrypt.BCrypt;

import com.example.analyticsapp.user.UserEntity;

/**
 * Utility class for hashing passwords during registration.
 */
public class HashingPassword {
    public static UserEntity hashPassword(UserRegisterRequest userRequest) {
        UserEntity newUserEntity = new UserEntity();
        // Generate a salt (a random value)
        String salt = BCrypt.gensalt();

        // Hash the password using the salt
        String hashedPassword = BCrypt.hashpw(userRequest.getPassword(), salt);
        newUserEntity.setEmail(userRequest.getEmail());
        newUserEntity.setPassword(hashedPassword);

        return newUserEntity;
    }

    public static String hashPassword(String plainPassword) {
        // Generate a salt (a random value)
        String salt = BCrypt.gensalt();

        // Hash the password using the salt
        String hashedPassword = BCrypt.hashpw(plainPassword, salt);

        return hashedPassword;
    }
}
