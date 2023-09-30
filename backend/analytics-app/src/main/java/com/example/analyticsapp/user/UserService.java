package com.example.analyticsapp.user;

import java.util.ArrayList;

/**
 * Service interface for managing user-related business logic.
 */
public interface UserService {
    /**
     * Get a list of all users.
     *
     * @return A list of user objects.
     */
    ArrayList<UserEntity> getAllUsers();

    /**
     * Get a user based on their username
     *
     * @return A user object.
     */
    UserEntity getOneUser(String username);

}
