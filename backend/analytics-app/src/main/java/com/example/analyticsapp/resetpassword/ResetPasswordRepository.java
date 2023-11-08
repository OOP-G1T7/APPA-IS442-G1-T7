package com.example.analyticsapp.resetpassword;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ResetPasswordRepository extends JpaRepository<ResetPasswordToken, Long>{
    ResetPasswordToken findByToken(String token);
}
