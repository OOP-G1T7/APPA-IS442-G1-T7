package com.example.analyticsapp.resetpassword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.analyticsapp.user.UserEntity;
import com.example.analyticsapp.user.UserRepository;

import org.mindrot.jbcrypt.BCrypt;

@RestController
@CrossOrigin
@RequestMapping("/api/password-reset")
public class ResetPasswordController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ResetPasswordService resetService;

    @Autowired
    private EmailService emailService;

    // Generates a token to be used for the api
    @PostMapping("/send-token")
    public void forgotPassword(@RequestBody ResetRequest resetRequest) {
        String email = resetRequest.getEmail();

        UserEntity user = userRepo.getUserByEmail(email);

        /*
         * if (user.equals(null)) {
         * return null;
         * }
         */

        ResetPasswordToken token = resetService.createToken(user);
        String tokenString = token.getToken();

        String message = "Please click on this link to reset your password:\nhttp://localhost:3000/ResetPassword/"
                + tokenString;
        emailService.sendEmail(email, "Reset Password", message);

    }

    @PostMapping("/{token}")
    public UserEntity resetPassword(@PathVariable String token, @RequestBody ChangeRequest changeRequest) {
        String password = changeRequest.getPassword();
        ResetPasswordToken tokenStored = resetService.findByToken(token);

        // check if the token generated exists in the database and whether it expired
        /*
         * if (tokenStored.equals(null) || tokenStored.hasExpired()) {
         * return null;
         * }
         */

        UserEntity user = tokenStored.getUser();
        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(password, salt);
        user.setPassword(hashedPassword);
        userRepo.save(user);
        return user;
    }

}
