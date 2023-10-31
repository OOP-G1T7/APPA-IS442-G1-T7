package com.example.analyticsapp.resetpassword;

public interface EmailService {
    
    void sendEmail(String toEmail, String emailSubject, String emailMsg);
    
}
