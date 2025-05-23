package com.ticketmanagement.demo.infrastructure.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ticketmanagement.demo.core.port.api.UserServicePort;

import lombok.extern.slf4j.Slf4j;

/**
 * Configuration for initializing demo data
 */
@Configuration
@Slf4j
public class DataInitializer {

    /**
     * Creates demo users on application startup
     */
    @Bean
    public CommandLineRunner initData(UserServicePort userService) {
        return args -> {
            checkDemoUsers(userService);
        };
    }
    
    private void checkDemoUsers(UserServicePort userService) {
        log.info("Checking if demo users exist...");
        
        try {
            if (userService.getUserByUsername("admin").isPresent()) {
                log.info("Admin user exists");
            } else {
                log.warn("Admin user does not exist!");
            }
            
            if (userService.getUserByUsername("user").isPresent()) {
                log.info("Regular user exists");
            } else {
                log.warn("Regular user does not exist!");
            }
        } catch (Exception e) {
            log.error("Error checking demo users", e);
        }
    }
}