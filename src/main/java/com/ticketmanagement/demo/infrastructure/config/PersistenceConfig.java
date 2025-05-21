package com.ticketmanagement.demo.infrastructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Persistence configuration for database access
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.ticketmanagement.demo.infrastructure.persistence.repository")
@EntityScan("com.ticketmanagement.demo.infrastructure.persistence.entity")
public class PersistenceConfig {
    // Persistence configuration settings
}
