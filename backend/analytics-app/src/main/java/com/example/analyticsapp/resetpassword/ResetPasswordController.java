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



@RestController
@CrossOrigin
@RequestMapping("/api/password-reset")
public class ResetPasswordController {


    @Autowired
    private ResetPasswordService resetService;



    // Generates a token to be used for the api
    @PostMapping("/send-token")
    public ResponseEntity<?> forgotPassword(@RequestBody ResetRequest resetRequest) {
        return resetService.forgotPassword(resetRequest);
    }

    @PostMapping("/{token}")
    public ResponseEntity<?> resetPassword(@PathVariable String token, @RequestBody ChangeRequest changeRequest) {
        return resetService.resetPassword(token, changeRequest);

    }

}
