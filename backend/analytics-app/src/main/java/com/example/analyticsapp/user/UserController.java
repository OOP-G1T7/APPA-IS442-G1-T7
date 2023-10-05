package com.example.analyticsapp.user;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * Retrieve a list of all users.
     *
     * @return A list of UserEntity objects.
     */
    @GetMapping("/get-all")
    public ArrayList<UserEntity> getAllUsers() {
        ArrayList<UserEntity> result = userService.getAllUsers();
        return result;
    }

    /**
     * Retrieve a one user based on their username.
     *
     * @return An UserEntity object.
     */
    @GetMapping("/{userId}")
    public UserEntity getOneUser(@PathVariable int userId) {
        UserEntity result = userService.getOneUser(userId);
        return result;
    }

}
