package com.ticketmanagement.demo.infrastructure.persistence.adapter;

import com.ticketmanagement.demo.core.domain.entity.Event;
import com.ticketmanagement.demo.core.domain.entity.Ticket;
import com.ticketmanagement.demo.core.domain.entity.TicketStatus;
import com.ticketmanagement.demo.core.port.spi.EventRepositoryPort;
import com.ticketmanagement.demo.infrastructure.persistence.entity.EventJpaEntity;
import com.ticketmanagement.demo.infrastructure.persistence.entity.TicketJpaEntity;
import com.ticketmanagement.demo.infrastructure.persistence.repository.EventJpaRepository;
import com.ticketmanagement.demo.infrastructure.persistence.repository.TicketJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Adapter for Event repository operations
 */
@Component
public class EventRepositoryAdapter extends BaseRepositoryAdapter<Event, EventJpaEntity> implements EventRepositoryPort {
    
    private final TicketJpaRepository ticketJpaRepository;
    
    public EventRepositoryAdapter(EventJpaRepository eventJpaRepository, TicketJpaRepository ticketJpaRepository) {
        super(eventJpaRepository);
        this.ticketJpaRepository = ticketJpaRepository;
    }
    
    @Override
    protected Event mapToEntity(EventJpaEntity jpaEntity) {
        return Event.builder()
                .id(jpaEntity.getId())
                .name(jpaEntity.getName())
                .date(jpaEntity.getDate())
                .venue(jpaEntity.getVenue())
                .createdAt(jpaEntity.getCreatedAt())
                .updatedAt(jpaEntity.getUpdatedAt())
                .build();
    }
    
    @Override
    protected EventJpaEntity mapToJpaEntity(Event entity) {
        return EventJpaEntity.builder()
                .id(entity.getId())
                .name(entity.getName())
                .date(entity.getDate())
                .venue(entity.getVenue())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
    
    @Override
    public List<Ticket> findTicketsByEventId(UUID eventId) {
        return ticketJpaRepository.findByEventId(eventId).stream()
                .map(this::mapToTicketEntity)
                .collect(Collectors.toList());
    }
    
    private Ticket mapToTicketEntity(TicketJpaEntity jpaEntity) {
        return Ticket.builder()
                .id(jpaEntity.getId())
                .code(jpaEntity.getCode())
                .eventId(jpaEntity.getEvent().getId())
                .attendeeName(jpaEntity.getAttendeeName())
                .status(TicketStatus.valueOf(jpaEntity.getStatus().name()))
                .purchaseDate(jpaEntity.getPurchaseDate())
                .createdAt(jpaEntity.getCreatedAt())
                .updatedAt(jpaEntity.getUpdatedAt())
                .build();
    }
}