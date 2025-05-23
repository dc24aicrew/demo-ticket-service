package com.ticketmanagement.demo.core.port.api;

import com.ticketmanagement.demo.core.domain.entity.User;

import java.util.Optional;
import java.util.UUID;

/**
 * Service port for User entity operations
 */
public interface UserServicePort {
    
    /**
     * Creates a new user
     * @param user The user to create
     * @return The created user
     */
    User createUser(User user);
    
    /**
     * Retrieves a user by ID
     * @param id The ID of the user to retrieve
     * @return Optional containing the user if found
     */
    Optional<User> getUserById(UUID id);
    
    /**
     * Retrieves a user by username
     * @param username The username of the user to retrieve
     * @return Optional containing the user if found
     */
    Optional<User> getUserByUsername(String username);
    
    /**
     * Validates a user's credentials
     * @param username The username to validate
     * @param password The password to validate
     * @return true if credentials are valid, false otherwise
     */
    boolean validateCredentials(String username, String password);
}