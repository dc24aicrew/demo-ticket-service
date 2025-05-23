package com.ticketmanagement.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderTest implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(PasswordEncoderTest.class);
    
    private final PasswordEncoder passwordEncoder;
    
    public PasswordEncoderTest(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public void run(String... args) {
        // Test passwords
        String rawPassword1 = "admin123";
        String encodedPasswordFromDB = "$2a$10$XlS5.8wVzLhb6hNShoZW7.viWvwLpe7nD7H7XlCWkc6D9TD/T3vfG";
        
        String rawPassword2 = "user123";
        String encodedPasswordFromDB2 = "$2a$10$oN0JK7RU7eUB3KYmnn8eau59xMPDFt3.YfHrmccQXSdjHVkjJrJnG";
        
        // Create a new encoded password to see the format
        String newEncodedPassword = passwordEncoder.encode(rawPassword1);
        
        // Check if the passwordEncoder can verify these passwords
        boolean result1 = passwordEncoder.matches(rawPassword1, encodedPasswordFromDB);
        boolean result2 = passwordEncoder.matches(rawPassword2, encodedPasswordFromDB2);
        boolean result3 = passwordEncoder.matches(rawPassword1, newEncodedPassword);
        
        // Log the results
        logger.info("=============== PASSWORD ENCODER TEST ===============");
        logger.info("Raw password 1: {}", rawPassword1);
        logger.info("Encoded password from DB 1: {}", encodedPasswordFromDB);
        logger.info("New encoded password: {}", newEncodedPassword);
        logger.info("PasswordEncoder.matches(rawPassword1, encodedPasswordFromDB): {}", result1);
        logger.info("PasswordEncoder.matches(rawPassword2, encodedPasswordFromDB2): {}", result2);
        logger.info("PasswordEncoder.matches(rawPassword1, newEncodedPassword): {}", result3);
        logger.info("====================================================");
    }
}
