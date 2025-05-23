package com.ticketmanagement.demo.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordEncoderTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void generateEncodedPasswords() {
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
