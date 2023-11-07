package com.example.analyticsapp.resetpassword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailServiceImplementation implements EmailService{
    
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String toEmail, String emailSubject, String emailMsg) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("oopg1t7@gmail.com");
        message.setTo(toEmail);
        message.setSubject(emailSubject);
        message.setText(emailMsg);
        javaMailSender.send(message);
    }

}
