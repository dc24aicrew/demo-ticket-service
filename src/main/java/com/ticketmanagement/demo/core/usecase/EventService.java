package com.ticketmanagement.demo.core.usecase;

import com.ticketmanagement.demo.core.domain.entity.Event;
import com.ticketmanagement.demo.core.domain.entity.Ticket;
import com.ticketmanagement.demo.core.port.api.EventServicePort;
import com.ticketmanagement.demo.core.port.spi.EventRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service implementation for Event-related operations
 */
@Service
public class EventService implements EventServicePort {
    
    private final EventRepositoryPort eventRepositoryPort;
    
    public EventService(EventRepositoryPort eventRepositoryPort) {
        this.eventRepositoryPort = eventRepositoryPort;
    }
    
    @Override
    public List<Event> getAllEvents() {
        return eventRepositoryPort.findAll();
    }
    
    @Override
    public Optional<Event> getEventById(UUID id) {
        return eventRepositoryPort.findById(id);
    }
    
    @Override
    public List<Ticket> getTicketsByEventId(UUID eventId) {
        return eventRepositoryPort.findTicketsByEventId(eventId);
    }
    
    @Override
    public Event save(Event event) {
        return eventRepositoryPort.save(event);
    }
}