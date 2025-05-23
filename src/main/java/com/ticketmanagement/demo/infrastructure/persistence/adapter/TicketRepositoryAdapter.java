package com.ticketmanagement.demo.infrastructure.persistence.adapter;

import com.ticketmanagement.demo.core.domain.entity.Event;
import com.ticketmanagement.demo.core.domain.entity.Ticket;
import com.ticketmanagement.demo.core.domain.entity.TicketStatus;
import com.ticketmanagement.demo.core.port.spi.TicketRepositoryPort;
import com.ticketmanagement.demo.infrastructure.persistence.entity.EventJpaEntity;
import com.ticketmanagement.demo.infrastructure.persistence.entity.TicketJpaEntity;
import com.ticketmanagement.demo.infrastructure.persistence.entity.TicketStatusJpa;
import com.ticketmanagement.demo.infrastructure.persistence.repository.TicketJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Ticket repository adapter implementing the ticket repository port
 */
@Component
public class TicketRepositoryAdapter extends BaseRepositoryAdapter<Ticket, TicketJpaEntity> implements TicketRepositoryPort {

    private final TicketJpaRepository ticketJpaRepository;

    public TicketRepositoryAdapter(TicketJpaRepository ticketJpaRepository) {
        super(ticketJpaRepository);
        this.ticketJpaRepository = ticketJpaRepository;
    }

    @Override
    public Optional<Ticket> findByCode(String code) {
        return ticketJpaRepository.findByCode(code)
                .map(this::mapToEntity);
    }

    @Override
    public List<Ticket> findByEventId(UUID eventId) {
        return ticketJpaRepository.findByEventId(eventId).stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Ticket> findByAttendeeName(String attendeeName) {
        // If the repository doesn't provide this method directly, we can filter in memory
        // In a real application, this should be implemented with a database query
        return findAll().stream()
                .filter(ticket -> ticket.getAttendeeName() != null && 
                        ticket.getAttendeeName().toLowerCase().contains(attendeeName.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Ticket> findByStatus(TicketStatus status) {
        return ticketJpaRepository.findByStatus(mapToJpaStatus(status)).stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }

    @Override
    protected Ticket mapToEntity(TicketJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }

        return Ticket.builder()
                .id(jpaEntity.getId())
                .code(jpaEntity.getCode())
                .eventId(jpaEntity.getEvent() != null ? jpaEntity.getEvent().getId() : null)
                .attendeeName(jpaEntity.getAttendeeName())
                .status(mapToDomainStatus(jpaEntity.getStatus()))
                .purchaseDate(jpaEntity.getPurchaseDate())
                .createdAt(jpaEntity.getCreatedAt())
                .updatedAt(jpaEntity.getUpdatedAt())
                .build();
    }

    @Override
    protected TicketJpaEntity mapToJpaEntity(Ticket entity) {
        if (entity == null) {
            return null;
        }

        TicketJpaEntity jpaEntity = new TicketJpaEntity();
        jpaEntity.setId(entity.getId());
        jpaEntity.setCode(entity.getCode());
        
        // Set event if eventId is provided
        if (entity.getEventId() != null) {
            EventJpaEntity eventJpaEntity = new EventJpaEntity();
            eventJpaEntity.setId(entity.getEventId());
            jpaEntity.setEvent(eventJpaEntity);
        }
        
        jpaEntity.setAttendeeName(entity.getAttendeeName());
        jpaEntity.setStatus(mapToJpaStatus(entity.getStatus()));
        jpaEntity.setPurchaseDate(entity.getPurchaseDate());
        jpaEntity.setCreatedAt(entity.getCreatedAt());
        jpaEntity.setUpdatedAt(entity.getUpdatedAt());
        
        return jpaEntity;
    }

    private TicketStatus mapToDomainStatus(TicketStatusJpa statusJpa) {
        if (statusJpa == null) {
            return null;
        }
        return TicketStatus.valueOf(statusJpa.name());
    }

    private TicketStatusJpa mapToJpaStatus(TicketStatus status) {
        if (status == null) {
            return null;
        }
        return TicketStatusJpa.valueOf(status.name());
    }
}