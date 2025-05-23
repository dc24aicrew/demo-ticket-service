package com.ticketmanagement.demo.infrastructure.persistence.adapter;

import com.ticketmanagement.demo.core.domain.entity.User;
import com.ticketmanagement.demo.core.port.spi.UserRepositoryPort;
import com.ticketmanagement.demo.infrastructure.persistence.entity.UserJpaEntity;
import com.ticketmanagement.demo.infrastructure.persistence.repository.UserJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Adapter for User repository operations
 */
@Component
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository userRepository;

    public UserRepositoryAdapter(UserJpaRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        UserJpaEntity entity = mapToJpaEntity(user);
        UserJpaEntity savedEntity = userRepository.save(entity);
        return mapToDomainEntity(savedEntity);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userRepository.findById(id).map(this::mapToDomainEntity);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username).map(this::mapToDomainEntity);
    }

    @Override
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    /**
     * Maps a domain entity to a JPA entity
     * @param user The domain entity to map
     * @return The mapped JPA entity
     */
    private UserJpaEntity mapToJpaEntity(User user) {
        return UserJpaEntity.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    /**
     * Maps a JPA entity to a domain entity
     * @param entity The JPA entity to map
     * @return The mapped domain entity
     */
    private User mapToDomainEntity(UserJpaEntity entity) {
        return User.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .roles(entity.getRoles())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}