package com.example.analyticsapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.analyticsapp.common.ApiResponse;

/**
 * Controller class for managing user-related operations.
 * This controller handles HTTP requests and interacts with the UserService.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String userHello() {
        return "Hello User!";
    }

    /**
     * Allows a user to register an account.
     *
     * @return The successfully created UserEntity object.
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> registerUser(@RequestBody UserEntity userEntity) {
        try {
            UserEntity registeredUserEntity = userService.register(userEntity);
            ApiResponse<UserEntity> response = new ApiResponse<UserEntity>(201, "Successfully registered new user!",
                    registeredUserEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (DataIntegrityViolationException e) {
            ApiResponse<String> response = new ApiResponse<String>(500, "Error in running the SQL query!",
                    e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (InvalidPasswordException e) {
            ApiResponse<String> response = new ApiResponse<String>(500, "The password is invalid!",
                    e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Allows a user to log into an account.
     *
     * @return A JWT token.
     */
    @PostMapping("/login")
    public String loginUser(@RequestBody UserEntity userEntity) {
        return "Login!";
    }
}
