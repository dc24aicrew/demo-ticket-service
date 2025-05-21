package com.ticketmanagement.demo.infrastructure.persistence.repository;

import com.ticketmanagement.demo.infrastructure.persistence.entity.BaseJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

/**
 * Base JPA repository interface for all persistence repositories
 */
@NoRepositoryBean
public interface BaseJpaRepository<T extends BaseJpaEntity> extends JpaRepository<T, UUID> {
    // Common JPA repository methods defined by Spring Data JPA
}
