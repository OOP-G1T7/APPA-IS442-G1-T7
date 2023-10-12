package com.example.analyticsapp.user;

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
    public UserEntity register(UserEntity userEntity) throws InvalidPasswordException {
        // Password Validation
        try {
            PasswordValidation.validatePassword(userEntity.getPassword());
            UserEntity newUserEntity = HashingPassword.hashPassword(userEntity);
            return userRepository.save(newUserEntity);
        } finally {
        }
    }

}
