package com.ticketmanagement.demo.infrastructure.persistence.repository;

import com.ticketmanagement.demo.infrastructure.persistence.entity.TicketJpaEntity;
import com.ticketmanagement.demo.infrastructure.persistence.entity.TicketStatusJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * JPA repository for Ticket entity operations
 */
@Repository
public interface TicketJpaRepository extends BaseJpaRepository<TicketJpaEntity> {
    Optional<TicketJpaEntity> findByCode(String code);
    List<TicketJpaEntity> findByEventId(UUID eventId);
    List<TicketJpaEntity> findByStatus(TicketStatusJpa status);
}