package com.example.analyticsapp.user;

/**
 * Service interface for managing user-related business logic.
 */
public interface UserService {
    /**
     * Register a user.
     *
     * @return The successfully created UserEntity object.
     */
    UserEntity register(UserEntity userEntity);

    /**
     * Log a user in.
     *
     * @return A JWT Token.
     */
    // String login(UserEntity userEntity);
}
