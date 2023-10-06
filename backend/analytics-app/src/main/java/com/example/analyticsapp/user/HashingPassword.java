package com.example.analyticsapp.user;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class for hashing passwords during registration.
 */
public class HashingPassword {
    public static UserEntity hashPassword(UserEntity userEntity) {
        UserEntity newUserEntity = new UserEntity();
        // Generate a salt (a random value)
        String salt = BCrypt.gensalt();

        // Hash the password using the salt
        String hashedPassword = BCrypt.hashpw(userEntity.getPassword(), salt);
        newUserEntity.setEmail(userEntity.getEmail());
        newUserEntity.setPassword(hashedPassword);

        return newUserEntity;
    }
}
