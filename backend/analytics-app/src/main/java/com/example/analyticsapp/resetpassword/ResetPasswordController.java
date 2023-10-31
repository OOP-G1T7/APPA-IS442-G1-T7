package com.example.analyticsapp.resetpassword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.analyticsapp.user.UserEntity;
import com.example.analyticsapp.user.UserRepository;
import com.example.analyticsapp.user.UserService;
import com.example.analyticsapp.user.util.HashingPassword;

import org.mindrot.jbcrypt.BCrypt;

@RestController
@RequestMapping("/password-reset")
public class ResetPasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ResetPasswordService resetService;

    @Autowired
    private EmailService emailService;

    


    // Generates a token to be used for the api
    @PostMapping("/send-token")
    public void forgotPassword(@RequestParam String email) {
        UserEntity user = userRepo.getUserByEmail(email);

        /* 
        if (user.equals(null)) {
            return null;
        }
        */

        ResetPasswordToken token = resetService.createToken(user);
        String tokenString = token.getToken();

        
        String message = "Please click on this link to reset your password:\nlocalhost:8080/password-reset/" + tokenString;
        emailService.sendEmail(email, "Reset Password", message);
        
    }

    @PostMapping("/{token}")
    public UserEntity resetPassword(@PathVariable String token, @RequestParam String password) {
        ResetPasswordToken tokenStored = resetService.findByToken(token);

        // check if the token generated exists in the database and whether it expired
        /* 
        if (tokenStored.equals(null) || tokenStored.hasExpired()) {
            return null; 
        } */

        UserEntity user = tokenStored.getUser();
        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(password, salt);
        user.setPassword(hashedPassword);
        userRepo.save(user);
        return user;
    }

}
