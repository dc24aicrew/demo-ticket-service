package com.ticketmanagement.demo.core.port.spi;

import com.ticketmanagement.demo.core.domain.entity.User;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository port for User entity operations
 */
public interface UserRepositoryPort {
    
    /**
     * Saves a user to the repository
     * @param user The user to save
     * @return The saved user
     */
    User save(User user);
    
    /**
     * Finds a user by ID
     * @param id The ID of the user to find
     * @return Optional containing the user if found
     */
    Optional<User> findById(UUID id);
    
    /**
     * Finds a user by username
     * @param username The username of the user to find
     * @return Optional containing the user if found
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Deletes a user from the repository
     * @param id The ID of the user to delete
     */
    void deleteById(UUID id);
}