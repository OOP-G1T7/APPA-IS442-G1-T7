package com.example.analyticsapp.user;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implementation for managing user-related business logic.
 */
@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public ArrayList<UserEntity> getAllUsers() {
        ArrayList<UserEntity> result = userRepository.getAllUsers();
        return result;
    }

    @Override
    public UserEntity getOneUser(String username) {
        UserEntity result = userRepository.getOneUser(username);
        return result;
    }

}
