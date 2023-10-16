package com.example.analyticsapp.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing CRUD operations on User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    /**
     * Find a user by their email.
     *
     * @param email The email to search for.
     * @return The user if found, or null if not found.
     */
    @Query(value = "SELECT * FROM user where email = ?", nativeQuery = true)
    UserEntity getUserByEmail(String email);
}
