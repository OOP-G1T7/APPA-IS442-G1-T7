package com.example.analyticsapp.user;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing CRUD operations on User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    /**
     * Find all users.
     *
     * @return The user if found, or null if not found.
     */
    @Query(value = "SELECT * FROM user", nativeQuery = true)
    ArrayList<UserEntity> getAllUsers();

    /**
     * Find all user by their userId.
     *
     * @param userId The username to search for.
     * @return The user if found, or null if not found.
     */
    @Query(value = "SELECT * FROM user where user_id = ?", nativeQuery = true)
    UserEntity getOneUser(int userId);

}
