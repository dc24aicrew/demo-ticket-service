package com.ticketmanagement.demo.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderTest {

    @Test
    void generateEncodedPasswords() {
        // Create an instance of PasswordEncoder directly
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        
        // Generate encoded passwords for data.sql
        String rawAdminPassword = "admin123";
        String rawUserPassword = "user123";
        
        String encodedAdminPassword = passwordEncoder.encode(rawAdminPassword);
        String encodedUserPassword = passwordEncoder.encode(rawUserPassword);
        
        System.out.println("====== GENERATED PASSWORDS FOR DATA.SQL ======");
        System.out.println("Admin password encoded: " + encodedAdminPassword);
        System.out.println("User password encoded: " + encodedUserPassword);
        System.out.println("==============================================");
        
        // Verify encoding works
        boolean adminMatches = passwordEncoder.matches(rawAdminPassword, encodedAdminPassword);
        boolean userMatches = passwordEncoder.matches(rawUserPassword, encodedUserPassword);
        
        System.out.println("Admin password verification: " + adminMatches);
        System.out.println("User password verification: " + userMatches);
    }
}
