package com.example.analyticsapp.resetpassword;

import com.example.analyticsapp.user.UserEntity;

public interface ResetPasswordService {
    
    ResetPasswordToken createToken(UserEntity user);

    ResetPasswordToken findByToken(String token);

    void deleteToken(ResetPasswordToken token);
}
