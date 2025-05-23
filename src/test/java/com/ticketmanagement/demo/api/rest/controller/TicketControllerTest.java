package com.ticketmanagement.demo.api.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketmanagement.demo.api.rest.dto.TicketDto;
import com.ticketmanagement.demo.api.rest.dto.TicketStatusUpdateDto;
import com.ticketmanagement.demo.api.rest.mapper.TicketMapper;
import com.ticketmanagement.demo.core.domain.entity.Ticket;
import com.ticketmanagement.demo.core.domain.entity.TicketStatus;
import com.ticketmanagement.demo.core.port.api.TicketServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TicketController.class)
class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketServicePort ticketService;

    @MockBean
    private TicketMapper ticketMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Ticket ticket;
    private TicketDto ticketDto;
    private UUID ticketId;

    @BeforeEach
    void setUp() {
        ticketId = UUID.randomUUID();
        UUID eventId = UUID.randomUUID();

        // Set up test ticket entity
        ticket = Ticket.builder()
                .id(ticketId)
                .code("TICKET123")
                .eventId(eventId)
                .attendeeName("John Doe")
                .status(TicketStatus.ACTIVE)
                .purchaseDate(OffsetDateTime.now())
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();

        // Set up DTO that the mapper would return
        ticketDto = TicketDto.builder()
                .id(ticketId)
                .code("TICKET123")
                .eventId(eventId)
                .attendeeName("John Doe")
                .status("ACTIVE")
                .purchaseDate(ticket.getPurchaseDate())
                .createdAt(ticket.getCreatedAt())
                .updatedAt(ticket.getUpdatedAt())
                .build();

        // Configure the mocked mapper
        when(ticketMapper.toDto(ticket)).thenReturn(ticketDto);
        when(ticketMapper.toDtoList(any())).thenAnswer(invocation -> {
            List<Ticket> tickets = invocation.getArgument(0);
            return tickets.stream()
                    .map(t -> TicketDto.builder()
                            .id(t.getId())
                            .code(t.getCode())
                            .eventId(t.getEventId())
                            .attendeeName(t.getAttendeeName())
                            .status(t.getStatus().name())
                            .purchaseDate(t.getPurchaseDate())
                            .createdAt(t.getCreatedAt())
                            .updatedAt(t.getUpdatedAt())
                            .build())
                    .toList();
        });
        when(ticketMapper.toEntity(any(TicketDto.class))).thenReturn(ticket);
    }

    @Test
    @WithMockUser
    void findAll_ShouldReturnAllTickets() throws Exception {
        List<Ticket> tickets = Arrays.asList(ticket);
        when(ticketService.findAll()).thenReturn(tickets);

        mockMvc.perform(get("/api/tickets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].code", is("TICKET123")))
                .andExpect(jsonPath("$[0].attendeeName", is("John Doe")));
    }

    @Test
    @WithMockUser
    void findById_ShouldReturnTicket() throws Exception {
        when(ticketService.findById(ticketId)).thenReturn(Optional.of(ticket));

        mockMvc.perform(get("/api/tickets/" + ticketId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("TICKET123")));
    }

    @Test
    @WithMockUser
    void searchTicketsByCode_ShouldReturnTicket() throws Exception {
        when(ticketService.findByCode("TICKET123")).thenReturn(Optional.of(ticket));

        mockMvc.perform(get("/api/tickets/search")
                        .param("code", "TICKET123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].code", is("TICKET123")));
    }

    @Test
    @WithMockUser
    void searchTicketsByEventId_ShouldReturnTickets() throws Exception {
        UUID eventId = ticket.getEventId();
        List<Ticket> tickets = Arrays.asList(ticket);
        when(ticketService.findByEventId(eventId)).thenReturn(tickets);

        mockMvc.perform(get("/api/tickets/search")
                        .param("eventId", eventId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].eventId", is(eventId.toString())));
    }

    @Test
    @WithMockUser
    void searchTicketsByAttendeeName_ShouldReturnTickets() throws Exception {
        List<Ticket> tickets = Arrays.asList(ticket);
        when(ticketService.findByAttendeeName("John")).thenReturn(tickets);

        mockMvc.perform(get("/api/tickets/search")
                        .param("attendeeName", "John")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].attendeeName", is("John Doe")));
    }

    @Test
    @WithMockUser
    void searchTicketsByStatus_ShouldReturnTickets() throws Exception {
        List<Ticket> tickets = Arrays.asList(ticket);
        when(ticketService.findByStatus(TicketStatus.ACTIVE)).thenReturn(tickets);

        mockMvc.perform(get("/api/tickets/search")
                        .param("status", "ACTIVE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].status", is("ACTIVE")));
    }

    @Test
    @WithMockUser
    void updateTicketStatus_ShouldUpdateStatus() throws Exception {
        TicketStatusUpdateDto statusUpdateDto = new TicketStatusUpdateDto("USED");

        // Updated ticket with USED status
        Ticket updatedTicket = Ticket.builder()
                .id(ticketId)
                .code("TICKET123")
                .eventId(ticket.getEventId())
                .attendeeName("John Doe")
                .status(TicketStatus.USED)
                .purchaseDate(ticket.getPurchaseDate())
                .createdAt(ticket.getCreatedAt())
                .updatedAt(OffsetDateTime.now())
                .build();

        // Updated DTO
        TicketDto updatedTicketDto = TicketDto.builder()
                .id(ticketId)
                .code("TICKET123")
                .eventId(ticket.getEventId())
                .attendeeName("John Doe")
                .status("USED")
                .purchaseDate(ticket.getPurchaseDate())
                .createdAt(ticket.getCreatedAt())
                .updatedAt(updatedTicket.getUpdatedAt())
                .build();

        when(ticketService.updateStatus(eq(ticketId), eq(TicketStatus.USED))).thenReturn(updatedTicket);
        when(ticketMapper.toDto(updatedTicket)).thenReturn(updatedTicketDto);

        mockMvc.perform(patch("/api/tickets/" + ticketId + "/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(statusUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("USED")));
    }

    @Test
    @WithMockUser
    void updateTicketStatus_WithInvalidStatus_ShouldReturnBadRequest() throws Exception {
        TicketStatusUpdateDto statusUpdateDto = new TicketStatusUpdateDto("INVALID_STATUS");

        mockMvc.perform(patch("/api/tickets/" + ticketId + "/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(statusUpdateDto)))
                .andExpect(status().isBadRequest());
    }
}