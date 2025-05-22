package com.ticketmanagement.demo.infrastructure.persistence.repository;

import com.ticketmanagement.demo.infrastructure.persistence.entity.EventJpaEntity;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for Event entity operations
 */
@Repository
public interface EventJpaRepository extends BaseJpaRepository<EventJpaEntity> {
}