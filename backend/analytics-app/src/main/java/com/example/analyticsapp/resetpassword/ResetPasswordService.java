package com.example.analyticsapp.resetpassword;

import org.springframework.http.ResponseEntity;


import com.example.analyticsapp.user.UserEntity;

public interface ResetPasswordService {
    
    ResetPasswordToken createToken(UserEntity user);

    ResetPasswordToken findByToken(String token);

    void deleteToken(ResetPasswordToken token);

    public ResponseEntity<?> forgotPassword(ResetRequest resetRequest);

    public ResponseEntity<?> resetPassword(String token, ChangeRequest changeRequest);
}
