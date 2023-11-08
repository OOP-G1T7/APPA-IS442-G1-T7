package com.example.analyticsapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.analyticsapp.user.util.HashingPassword;
import com.example.analyticsapp.user.util.InvalidPasswordException;
import com.example.analyticsapp.user.util.PasswordValidation;
import com.example.analyticsapp.user.util.UserRegisterRequest;

/**
 * Service implementation for managing user-related business logic.
 */
@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserEntity register(UserRegisterRequest userRequest) throws InvalidPasswordException {
        // Password Validation
        PasswordValidation.validatePassword(userRequest.getPassword(), userRequest.getPasswordConfirm());
        UserEntity newUserEntity = HashingPassword.hashPassword(userRequest);
        return userRepository.save(newUserEntity);
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

}
