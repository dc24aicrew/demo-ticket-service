package com.ticketmanagement.demo.core.port.spi;

import com.ticketmanagement.demo.core.domain.entity.Event;
import com.ticketmanagement.demo.core.domain.entity.Ticket;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Port for Event repository operations
 */
public interface EventRepositoryPort extends BaseRepositoryPort<Event> {
    
    /**
     * Find all tickets for a specific event
     * @param eventId Event ID
     * @return List of tickets for the event
     */
    List<Ticket> findTicketsByEventId(UUID eventId);
}