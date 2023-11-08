package com.example.analyticsapp.resetpassword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.analyticsapp.common.ApiResponse;
import com.example.analyticsapp.resetpassword.util.TokenNotFoundException;
import com.example.analyticsapp.resetpassword.util.UserNotFoundException;
import com.example.analyticsapp.user.UserEntity;
import com.example.analyticsapp.user.UserRepository;
import com.example.analyticsapp.user.util.InvalidPasswordException;
import com.example.analyticsapp.user.util.PasswordValidation;

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
    public ResponseEntity<?> forgotPassword(@RequestBody ResetRequest resetRequest) {
        String email = resetRequest.getEmail();
        try {
            UserEntity user = userRepo.getUserByEmail(email);

            if (user == null) {
                throw new UserNotFoundException("User does not exist");
            }

            ResetPasswordToken token = resetService.createToken(user);
            String tokenString = token.getToken();

            String message = "Please click on this link to reset your password:\nhttp://localhost:3000/ResetPassword/"
                    + tokenString;
            emailService.sendEmail(email, "Reset Password", message);

            String result = "Email Sent";
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK.value(), "Success", result);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (UserNotFoundException e) {
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "User not found",
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }

    @PostMapping("/{token}")
    public ResponseEntity<?> resetPassword(@PathVariable String token, @RequestBody ChangeRequest changeRequest) {
        String password = changeRequest.getPassword();
        String confirmPassword = changeRequest.getConfirmPassword();

        try {
            ResetPasswordToken tokenStored = resetService.findByToken(token);

            if (tokenStored == null || tokenStored.hasExpired()) {
                throw new TokenNotFoundException("Token not found");
            }

            PasswordValidation.validatePassword(password, confirmPassword);

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
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Token not found",
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (InvalidPasswordException e) {
            ApiResponse<String> response = new ApiResponse<String>(500, "InvalidPasswordException",
                    e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

}
