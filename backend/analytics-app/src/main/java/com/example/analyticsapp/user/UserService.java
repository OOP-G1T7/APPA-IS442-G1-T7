package com.example.analyticsapp.user;

import com.example.analyticsapp.user.util.UserRegisterRequest;

/**
 * Service interface for managing user-related business logic.
 */
public interface UserService {
    /**
     * Register a user.
     *
     * @return The successfully created UserEntity object.
     */
    UserEntity register(UserRegisterRequest userRequest);

    UserEntity getUserByEmail(String email);

}
