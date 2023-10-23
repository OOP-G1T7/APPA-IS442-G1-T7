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
    private PasswordEncoder passwordEncoder;


    // Not sure how we want to give the token, so function output not set yet
    @PostMapping("/send-token")
    public String forgotPassword(@RequestParam String email) {
        UserEntity user = userRepo.getUserByEmail(email);

        if (user.equals(null)) {
            return "No such user";
        }

        ResetPasswordToken token = resetService.createToken(user);
        String tokenString = token.getToken();
        return tokenString;
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
        user.setPassword(password, passwordEncoder);
        userRepo.save(user);
        return user;
    }

}
