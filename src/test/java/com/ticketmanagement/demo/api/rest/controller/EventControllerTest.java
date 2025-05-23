package com.ticketmanagement.demo.api.rest.controller;

import com.ticketmanagement.demo.api.rest.dto.EventDto;
import com.ticketmanagement.demo.api.rest.mapper.EventMapper;
import com.ticketmanagement.demo.core.domain.entity.Event;
import com.ticketmanagement.demo.core.domain.entity.Ticket;
import com.ticketmanagement.demo.core.domain.entity.TicketStatus;
import com.ticketmanagement.demo.core.port.api.EventServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventControllerTest {

    @Mock
    private EventServicePort eventServicePort;

    @Mock
    private EventMapper eventMapper;

    @InjectMocks
    private EventController eventController;

    private Event testEvent;
    private EventDto testEventDto;
    private List<Event> testEvents;
    private List<Ticket> testTickets;
    private UUID eventId;

    @BeforeEach
    void setUp() {
        eventId = UUID.randomUUID();
        
        // Setup test event
        testEvent = Event.builder()
                .id(eventId)
                .name("Test Event")
                .venue("Test Venue")
                .date(OffsetDateTime.now().plusDays(30))
                .build();
        
        // Setup test event DTO
        testEventDto = EventDto.builder()
                .id(eventId)
                .name("Test Event")
                .venue("Test Venue")
                .date(OffsetDateTime.now().plusDays(30))
                .build();
        
        // Setup test events list
        testEvents = Arrays.asList(testEvent);
        
        // Setup test tickets
        UUID ticketId = UUID.randomUUID();
        Ticket testTicket = Ticket.builder()
                .id(ticketId)
                .code("TEST-001")
                .eventId(eventId)
                .attendeeName("Test Attendee")
                .status(TicketStatus.ACTIVE)
                .purchaseDate(OffsetDateTime.now())
                .build();
        testTickets = Arrays.asList(testTicket);
    }

    @Test
    void getAllEvents_ReturnsListOfEvents() {
        // Arrange
        when(eventServicePort.getAllEvents()).thenReturn(testEvents);
        when(eventMapper.toDto(testEvent)).thenReturn(testEventDto);
        
        // Act
        ResponseEntity<List<EventDto>> response = eventController.getAllEvents();
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(eventId, response.getBody().get(0).getId());
        assertEquals("Test Event", response.getBody().get(0).getName());
    }

    @Test
    void getEventById_WhenEventExists_ReturnsEvent() {
        // Arrange
        when(eventServicePort.getEventById(eventId)).thenReturn(Optional.of(testEvent));
        when(eventMapper.toDto(testEvent)).thenReturn(testEventDto);
        
        // Act
        ResponseEntity<EventDto> response = eventController.getEventById(eventId);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(eventId, response.getBody().getId());
        assertEquals("Test Event", response.getBody().getName());
    }

    @Test
    void getEventById_WhenEventDoesNotExist_Returns404() {
        // Arrange
        when(eventServicePort.getEventById(eventId)).thenReturn(Optional.empty());
        
        // Act
        ResponseEntity<EventDto> response = eventController.getEventById(eventId);
        
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getTicketsForEvent_WhenEventExists_ReturnsTickets() {
        // Arrange
        when(eventServicePort.getEventById(eventId)).thenReturn(Optional.of(testEvent));
        when(eventServicePort.getTicketsByEventId(eventId)).thenReturn(testTickets);
        
        // Act
        ResponseEntity<List<Ticket>> response = eventController.getTicketsForEvent(eventId);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("TEST-001", response.getBody().get(0).getCode());
        assertEquals(eventId, response.getBody().get(0).getEventId());
    }

    @Test
    void getTicketsForEvent_WhenEventDoesNotExist_Returns404() {
        // Arrange
        when(eventServicePort.getEventById(eventId)).thenReturn(Optional.empty());
        
        // Act
        ResponseEntity<List<Ticket>> response = eventController.getTicketsForEvent(eventId);
        
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}