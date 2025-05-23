package com.ticketmanagement.demo.infrastructure.config;

import com.ticketmanagement.demo.core.domain.entity.User;
import com.ticketmanagement.demo.core.port.api.UserServicePort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

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
            createDemoUsers(userService);
        };
    }
    
    private void createDemoUsers(UserServicePort userService) {
        if (userService.getUserByUsername("admin").isEmpty()) {
            User admin = User.builder()
                    .username("admin")
                    .password("admin123") // Password will be encoded by service
                    .roles("ROLE_ADMIN,ROLE_USER")
                    .createdAt(OffsetDateTime.now(ZoneOffset.UTC))
                    .updatedAt(OffsetDateTime.now(ZoneOffset.UTC))
                    .build();
                    
            userService.createUser(admin);
            log.info("Demo admin user created");
        }
        
        if (userService.getUserByUsername("user").isEmpty()) {
            User user = User.builder()
                    .username("user")
                    .password("user123") // Password will be encoded by service
                    .roles("ROLE_USER")
                    .createdAt(OffsetDateTime.now(ZoneOffset.UTC))
                    .updatedAt(OffsetDateTime.now(ZoneOffset.UTC))
                    .build();
                    
            userService.createUser(user);
            log.info("Demo regular user created");
        }
    }
}