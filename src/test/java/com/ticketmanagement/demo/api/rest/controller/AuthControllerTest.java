 package com.ticketmanagement.demo.api.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketmanagement.demo.api.rest.dto.AuthRequest;
import com.ticketmanagement.demo.core.domain.entity.User;
import com.ticketmanagement.demo.core.port.api.UserServicePort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private UserServicePort userService;
    
    @Test
    void loginWithValidCredentials() throws Exception {
        // Create a test user with raw password
        User user = User.builder()
                .username("testuser")
                .password("password123") // Will be encoded by service
                .roles("ROLE_USER")
                .createdAt(OffsetDateTime.now(ZoneOffset.UTC))
                .updatedAt(OffsetDateTime.now(ZoneOffset.UTC))
                .build();
        
        userService.createUser(user);
        
        AuthRequest request = new AuthRequest("testuser", "password123");
        
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.roles").value("ROLE_USER"));
    }
    
    @Test
    void loginWithInvalidCredentials() throws Exception {
        AuthRequest request = new AuthRequest("wronguser", "wrongpassword");
        
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }
    
    @Test
    void logoutSuccessful() throws Exception {
        mockMvc.perform(post("/auth/logout"))
                .andExpect(status().isOk());
    }
}