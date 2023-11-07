package com.example.analyticsapp.resetpassword;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.analyticsapp.user.UserEntity;

@Service
public class ResetPasswordServiceImplementation implements ResetPasswordService {

    @Autowired
    private ResetPasswordRepository tokenRepository;

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    public ResetPasswordToken createToken(UserEntity user) {
        String tokenString = generateToken();
        ResetPasswordToken token = new ResetPasswordToken(tokenString, user);
        return tokenRepository.save(token);
    }

    public ResetPasswordToken findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public void deleteToken(ResetPasswordToken token) {
        tokenRepository.delete(token);
    }
}
