package com.example.analyticsapp.resetpassword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.analyticsapp.common.ApiResponse;
import com.example.analyticsapp.resetpassword.util.TokenNotFoundException;
import com.example.analyticsapp.resetpassword.util.UserNotFoundException;
import com.example.analyticsapp.user.UserEntity;
import com.example.analyticsapp.user.UserRepository;
import com.example.analyticsapp.user.UserService;
import com.example.analyticsapp.user.util.HashingPassword;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        try{
            UserEntity user = userRepo.getUserByEmail(email);

         
            if (user == null) {
                throw new UserNotFoundException("User does not exist");
            }
        

            ResetPasswordToken token = resetService.createToken(user);
            String tokenString = token.getToken();

        
            String message = "Please click on this link to reset your password:\nlocalhost:8080/password-reset/" + tokenString;
            emailService.sendEmail(email, "Reset Password", message);

            String result = "Email Sent";
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK.value(), "Success", result);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (UserNotFoundException e){
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "User not found", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        
        
    }

    @PostMapping("/{token}")
    public ResponseEntity<?> resetPassword(@PathVariable String token, @RequestParam String password) {

        try {
            ResetPasswordToken tokenStored = resetService.findByToken(token);
 
            if (tokenStored == null || tokenStored.hasExpired()) {
                throw new TokenNotFoundException("Token not found");
            }

            UserEntity user = tokenStored.getUser();
            String salt = BCrypt.gensalt();
            String hashedPassword = BCrypt.hashpw(password, salt);
            user.setPassword(hashedPassword);
            userRepo.save(user);
            resetService.deleteToken(tokenStored);

            String result = "Password has been successfully reset";
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK.value(), "Success", result);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (TokenNotFoundException e) {
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Token not found", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }



        
    }

}
