package com.example.analyticsapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.analyticsapp.auth.LoginReq;
import com.example.analyticsapp.auth.LoginRes;
import com.example.analyticsapp.auth.jwt.JwtUtil;
import com.example.analyticsapp.common.ApiResponse;
import com.example.analyticsapp.user.util.InvalidPasswordException;
import com.example.analyticsapp.user.util.UserRegisterRequest;

/**
 * Controller class for managing user-related operations.
 * This controller handles HTTP requests and interacts with the UserService.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

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
}
