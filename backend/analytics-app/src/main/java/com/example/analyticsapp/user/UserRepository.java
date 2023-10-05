package com.example.analyticsapp.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing CRUD operations on User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
