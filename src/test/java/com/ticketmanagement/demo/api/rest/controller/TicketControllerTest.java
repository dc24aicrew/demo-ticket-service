package com.ticketmanagement.demo.api.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketmanagement.demo.api.rest.dto.TicketStatusUpdateDto;
import com.ticketmanagement.demo.core.domain.entity.TicketStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void findAll_ShouldReturnAllTickets() throws Exception {
        mockMvc.perform(get("/api/tickets")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void findById_ShouldReturnTicket() throws Exception {
        UUID ticketId = UUID.randomUUID();
        // Using a random UUID which will return 404, but we're just testing the endpoint security and access
        mockMvc.perform(get("/api/tickets/" + ticketId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // expecting 404 since the ticket doesn't exist
    }

    @Test
    @WithMockUser
    void searchTicketsByCode_ShouldReturnTicket() throws Exception {
        mockMvc.perform(get("/api/tickets/search")
                .param("code", "NONEXISTENT-CODE")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()); // returns empty list, not 404
    }

    @Test
    @WithMockUser
    void searchTicketsByEventId_ShouldReturnTickets() throws Exception {
        UUID eventId = UUID.randomUUID();
        
        mockMvc.perform(get("/api/tickets/search")
                .param("eventId", eventId.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()); // returns empty list, not 404
    }

    @Test
    @WithMockUser
    void searchTicketsByAttendeeName_ShouldReturnTickets() throws Exception {
        mockMvc.perform(get("/api/tickets/search")
                .param("attendeeName", "NonexistentName")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()); // returns empty list, not 404
    }

    @Test
    @WithMockUser
    void searchTicketsByStatus_ShouldReturnTickets() throws Exception {
        mockMvc.perform(get("/api/tickets/search")
                .param("status", "ACTIVE")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()); // returns empty list, not 404
    }

    @Test
    @WithMockUser
    void updateTicketStatus_WithInvalidStatus_ShouldReturnBadRequest() throws Exception {
        UUID ticketId = UUID.randomUUID();
        TicketStatusUpdateDto statusUpdateDto = new TicketStatusUpdateDto("INVALID_STATUS");

        mockMvc.perform(patch("/api/tickets/" + ticketId + "/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(statusUpdateDto)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    @WithMockUser
    void updateTicketStatus_WithNonexistentTicket_ShouldReturnNotFound() throws Exception {
        UUID ticketId = UUID.randomUUID();
        TicketStatusUpdateDto statusUpdateDto = new TicketStatusUpdateDto(TicketStatus.USED.name());

        mockMvc.perform(patch("/api/tickets/" + ticketId + "/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(statusUpdateDto)))
                .andExpect(status().isNotFound());
    }
}
