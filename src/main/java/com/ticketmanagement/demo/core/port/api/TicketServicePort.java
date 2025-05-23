package com.ticketmanagement.demo.core.port.api;

import com.ticketmanagement.demo.core.domain.entity.Ticket;
import com.ticketmanagement.demo.core.domain.entity.TicketStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service port for ticket-specific operations
 * Extends the base service port with ticket-specific methods
 */
public interface TicketServicePort extends BaseServicePort<Ticket> {
    
    /**
     * Find a ticket by its unique code
     *
     * @param code the ticket code
     * @return the ticket if found
     */
    Optional<Ticket> findByCode(String code);
    
    /**
     * Find tickets by event ID
     *
     * @param eventId the event ID
     * @return list of tickets for the event
     */
    List<Ticket> findByEventId(UUID eventId);
    
    /**
     * Find tickets by attendee name (case insensitive, partial match)
     *
     * @param attendeeName the attendee name to search for
     * @return list of tickets matching the attendee name
     */
    List<Ticket> findByAttendeeName(String attendeeName);
    
    /**
     * Find tickets by status
     *
     * @param status the ticket status
     * @return list of tickets with the given status
     */
    List<Ticket> findByStatus(TicketStatus status);
    
    /**
     * Update ticket status
     *
     * @param id the ticket ID
     * @param status the new status
     * @return the updated ticket
     */
    Ticket updateStatus(UUID id, TicketStatus status);
}