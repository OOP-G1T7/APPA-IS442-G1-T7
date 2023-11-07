package com.example.analyticsapp.user;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.analyticsapp.auth.LoginReq;
import com.example.analyticsapp.auth.LoginRes;
import com.example.analyticsapp.auth.jwt.JwtUtil;
import com.example.analyticsapp.common.ApiResponse;
import com.example.analyticsapp.user.util.ChangePasswordRequest;
import com.example.analyticsapp.user.util.HashingPassword;
import com.example.analyticsapp.user.util.InvalidPasswordException;
import com.example.analyticsapp.user.util.UserRegisterRequest;

/**
 * Controller class for managing user-related operations.
 * This controller handles HTTP requests and interacts with the UserService.
 */
@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository; // added for change password

    private final AuthenticationManager authenticationManager;

    private JwtUtil jwtUtil;

    public UserController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;

    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<String>> userHello() {
        ApiResponse<String> response = new ApiResponse<String>(200, "Successfully retrieved message!",
                "Hello User!");
        return ResponseEntity.ok().body(response);
    }

    /**
     * Allows a user to register an account.
     *
     * @return The successfully created UserEntity object.
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> registerUser(@RequestBody UserRegisterRequest userRequest) {
        try {
            UserEntity registeredUserEntity = userService.register(userRequest);
            ApiResponse<UserEntity> response = new ApiResponse<UserEntity>(201, "Successfully registered new user!",
                    registeredUserEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (DataIntegrityViolationException e) {
            ApiResponse<String> response = new ApiResponse<String>(500, "DataIntegrityViolationException",
                    e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (InvalidPasswordException e) {
            ApiResponse<String> response = new ApiResponse<String>(500, "InvalidPasswordException",
                    e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Allows a user to log into an account.
     *
     * @return A response with JWT token.
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<?>> login(@RequestBody LoginReq loginReq) {

        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));
            String email = authentication.getName();
            UserEntity user = userService.getUserByEmail(email);
            String token = jwtUtil.createToken(user);
            LoginRes loginRes = new LoginRes(email, token);
            ApiResponse<LoginRes> response = new ApiResponse<LoginRes>(201, "Successfully authenticated user!",
                    loginRes);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (BadCredentialsException e) {
            ApiResponse<String> response = new ApiResponse<String>(400, "Invalid username or password!",
                    e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<String>(400, "An unknown error occurred!",
                    e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Allows a user to change their password.
     *
     * @return A success response or an error response.
     */
    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<String>> changePassword(
            @RequestBody ChangePasswordRequest changePasswordRequest) {
        try {
            // Check if the email and current password match
            UserEntity user = userRepository.getUserByEmail(changePasswordRequest.getEmail());

            if (user != null) {
                String inputPassword = changePasswordRequest.getCurrentPassword();
                String storedHashedPassword = user.getPassword();
                String newPassword = changePasswordRequest.getNewPassword();
                String confirmPassword = changePasswordRequest.getConfirmPassword();

                // Print relevant information for debugging
                System.out.println("Email: " + changePasswordRequest.getEmail());
                System.out.println("Stored Hashed Password: " + storedHashedPassword);
                System.out.println("Input Password: " + changePasswordRequest.getCurrentPassword());

                if (BCrypt.checkpw(inputPassword, storedHashedPassword)) {
                    // Check if the new password matches the confirm password
                    if (newPassword.equals(confirmPassword)) {
                        // Hash the new password using the utility class
                        String newHashedPassword = HashingPassword.hashPassword(changePasswordRequest.getNewPassword());

                        // Update the password in the database
                        user.setPassword(newHashedPassword);
                        userRepository.save(user);

                        ApiResponse<String> response = new ApiResponse<>(200, "Password changed successfully!", null);
                        return ResponseEntity.ok(response);
                    } else {
                        // Passwords don't match
                        ApiResponse<String> response = new ApiResponse<>(400,
                                "New password and confirm password do not match.", null);
                        return ResponseEntity.badRequest().body(response);
                    }
                } else {
                    // Email and current password don't match
                    ApiResponse<String> response = new ApiResponse<>(400, "Email and current password do not match.",
                            null);
                    return ResponseEntity.badRequest().body(response);
                }
            } else {
                // User not found
                ApiResponse<String> response = new ApiResponse<>(400, "User not found.", null);
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            // Handle any other exceptions
            ApiResponse<String> response = new ApiResponse<>(500, "An error occurred while changing the password.",
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
