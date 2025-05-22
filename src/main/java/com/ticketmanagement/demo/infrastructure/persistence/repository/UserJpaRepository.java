package com.ticketmanagement.demo.infrastructure.persistence.repository;

import com.ticketmanagement.demo.infrastructure.persistence.entity.UserJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA repository for User entity operations
 */
@Repository
public interface UserJpaRepository extends BaseJpaRepository<UserJpaEntity> {
    Optional<UserJpaEntity> findByUsername(String username);
}