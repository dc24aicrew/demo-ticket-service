package com.ticketmanagement.demo.api.rest.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticketmanagement.demo.api.rest.dto.EventDto;
import com.ticketmanagement.demo.api.rest.mapper.EventMapper;
import com.ticketmanagement.demo.core.domain.entity.Event;
import com.ticketmanagement.demo.core.domain.entity.Ticket;
import com.ticketmanagement.demo.core.port.api.EventServicePort;

import lombok.RequiredArgsConstructor;

/**
 * REST controller for Event-related endpoints
 */
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    
    private final EventServicePort eventServicePort;
    private final EventMapper eventMapper;
    
    /**
     * Get all events
     * @return List of all events
     */
    @GetMapping
    public ResponseEntity<List<EventDto>> getAllEvents() {
        List<Event> events = eventServicePort.getAllEvents();
        return ResponseEntity.ok(events.stream()
                .map(eventMapper::toDto)
                .collect(Collectors.toList()));
    }
    
    /**
     * Get a specific event by ID
     * @param id Event ID
     * @return Event if found, 404 otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEventById(@PathVariable UUID id) {
        return eventServicePort.getEventById(id)
                .map(eventMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Get all tickets for a specific event
     * @param id Event ID
     * @return List of tickets for the event
     */
    @GetMapping("/{id}/tickets")
    public ResponseEntity<List<Ticket>> getTicketsForEvent(@PathVariable UUID id) {
        // First check if the event exists
        if (eventServicePort.getEventById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        List<Ticket> tickets = eventServicePort.getTicketsByEventId(id);
        return ResponseEntity.ok(tickets);
    }
}