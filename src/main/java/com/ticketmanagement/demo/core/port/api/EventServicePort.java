package com.ticketmanagement.demo.core.port.api;

import com.ticketmanagement.demo.core.domain.entity.Event;
import com.ticketmanagement.demo.core.domain.entity.Ticket;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Port for Event service operations
 */
public interface EventServicePort {
    
    /**
     * Retrieve all events
     * @return List of all events
     */
    List<Event> getAllEvents();
    
    /**
     * Get a specific event by ID
     * @param id Event ID
     * @return Optional containing the event if found
     */
    Optional<Event> getEventById(UUID id);
    
    /**
     * Get all tickets for a specific event
     * @param eventId Event ID
     * @return List of tickets for the event
     */
    List<Ticket> getTicketsByEventId(UUID eventId);
}