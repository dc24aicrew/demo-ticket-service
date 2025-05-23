package com.ticketmanagement.demo.core.usecase;

import com.ticketmanagement.demo.core.domain.entity.Ticket;
import com.ticketmanagement.demo.core.domain.entity.TicketStatus;
import com.ticketmanagement.demo.core.domain.exception.TicketNotFoundException;
import com.ticketmanagement.demo.core.port.api.TicketServicePort;
import com.ticketmanagement.demo.core.port.spi.TicketRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service implementation for ticket-specific operations
 */
@Service
public class TicketService extends BaseService<Ticket> implements TicketServicePort {

    private final TicketRepositoryPort ticketRepository;

    public TicketService(TicketRepositoryPort ticketRepository) {
        super(ticketRepository);
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Optional<Ticket> findByCode(String code) {
        return ticketRepository.findByCode(code);
    }

    @Override
    public List<Ticket> findByEventId(UUID eventId) {
        return ticketRepository.findByEventId(eventId);
    }

    @Override
    public List<Ticket> findByAttendeeName(String attendeeName) {
        return ticketRepository.findByAttendeeName(attendeeName);
    }

    @Override
    public List<Ticket> findByStatus(TicketStatus status) {
        return ticketRepository.findByStatus(status);
    }

    @Override
    @Transactional
    public Ticket updateStatus(UUID id, TicketStatus status) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with id: " + id));

        // Validate status transition
        validateStatusTransition(ticket, status);

        ticket.setStatus(status);
        ticket.setUpdatedAt(OffsetDateTime.now(ZoneOffset.UTC));

        return ticketRepository.save(ticket);
    }

    /**
     * Validates if the status transition is allowed
     * For now, we only have ACTIVE to USED transition
     * More complex transitions could be added in the future
     */
    private void validateStatusTransition(Ticket ticket, TicketStatus newStatus) {
        // Currently, we only check if trying to set the same status
        if (ticket.getStatus() == newStatus) {
            throw new IllegalStateException("Ticket is already in " + newStatus + " status");
        }

        // We could add additional validation rules based on business requirements
        // For example:
        // if (ticket.getStatus() == TicketStatus.USED && newStatus == TicketStatus.ACTIVE) {
        //     throw new IllegalStateException("Cannot change status from USED to ACTIVE");
        // }
    }
}