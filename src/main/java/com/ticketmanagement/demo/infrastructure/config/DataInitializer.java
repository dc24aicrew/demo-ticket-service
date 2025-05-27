package com.ticketmanagement.demo.infrastructure.config;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ticketmanagement.demo.core.domain.entity.Event;
import com.ticketmanagement.demo.core.domain.entity.Ticket;
import com.ticketmanagement.demo.core.domain.entity.TicketStatus;
import com.ticketmanagement.demo.core.domain.entity.User;
import com.ticketmanagement.demo.core.port.api.EventServicePort;
import com.ticketmanagement.demo.core.port.api.TicketServicePort;
import com.ticketmanagement.demo.core.port.api.UserServicePort;

import lombok.extern.slf4j.Slf4j;

/**
 * Configuration for initializing demo data
 */
@Configuration
@Slf4j
public class DataInitializer {

    /**
     * Creates demo data on application startup
     */    @Bean
    public CommandLineRunner initData(
            UserServicePort userService,
            EventServicePort eventService,
            TicketServicePort ticketService) {
        return args -> {
            // Check if data already exists before trying to create it
            if (userService.getUserByUsername("admin").isEmpty() || userService.getUserByUsername("user").isEmpty()) {
                createDemoUsers(userService);
            } else {
                log.info("Users already exist, skipping user creation");
            }
            
            if (eventService.getAllEvents().isEmpty()) {
                createDemoEvents(eventService, ticketService);
            } else {
                log.info("Events already exist, skipping event creation");
            }
            
            // Signal that initialization is complete
            log.info("Data initialization completed successfully");
        };
    }
    
    private void createDemoUsers(UserServicePort userService) {
        log.info("Initializing demo users...");
        
        try {
            // Create admin user if it doesn't exist
            if (userService.getUserByUsername("admin").isEmpty()) {
                log.info("Creating admin user...");
                User adminUser = User.builder()
                        .username("admin")
                        .password("admin123")
                        .roles("ADMIN")
                        .createdAt(OffsetDateTime.now(ZoneOffset.UTC))
                        .updatedAt(OffsetDateTime.now(ZoneOffset.UTC))
                        .build();
                userService.createUser(adminUser);
                log.info("Admin user created successfully");
            } else {
                log.info("Admin user already exists");
            }
            
            // Create regular user if it doesn't exist
            if (userService.getUserByUsername("user").isEmpty()) {
                log.info("Creating regular user...");
                User regularUser = User.builder()
                        .username("user")
                        .password("user123")
                        .roles("USER")
                        .createdAt(OffsetDateTime.now(ZoneOffset.UTC))
                        .updatedAt(OffsetDateTime.now(ZoneOffset.UTC))
                        .build();
                userService.createUser(regularUser);
                log.info("Regular user created successfully");
            } else {
                log.info("Regular user already exists");
            }
        } catch (Exception e) {
            log.error("Error initializing demo users", e);
        }
    }
    
    private void createDemoEvents(EventServicePort eventService, TicketServicePort ticketService) {
        log.info("Initializing demo events and tickets...");
        
        try {
            // Create demo events if none exist
            if (eventService.getAllEvents().isEmpty()) {
                log.info("Creating demo events...");
                
                // Event 1: Conference
                Event conference = Event.builder()
                        .name("Tech Conference 2023")
                        .date(OffsetDateTime.now(ZoneOffset.UTC).plus(10, ChronoUnit.DAYS))
                        .venue("Convention Center, Room 101")
                        .createdAt(OffsetDateTime.now(ZoneOffset.UTC))
                        .updatedAt(OffsetDateTime.now(ZoneOffset.UTC))
                        .build();
                Event savedConference = eventService.save(conference);
                createDemoTicketsForEvent(savedConference, ticketService, 5);
                
                // Event 2: Workshop
                Event workshop = Event.builder()
                        .name("JavaScript Workshop")
                        .date(OffsetDateTime.now(ZoneOffset.UTC).plus(15, ChronoUnit.DAYS))
                        .venue("Learning Center, Lab 3")
                        .createdAt(OffsetDateTime.now(ZoneOffset.UTC))
                        .updatedAt(OffsetDateTime.now(ZoneOffset.UTC))
                        .build();
                Event savedWorkshop = eventService.save(workshop);
                createDemoTicketsForEvent(savedWorkshop, ticketService, 3);
                
                // Event 3: Networking
                Event networking = Event.builder()
                        .name("Tech Networking Mixer")
                        .date(OffsetDateTime.now(ZoneOffset.UTC).plus(5, ChronoUnit.DAYS))
                        .venue("Startup Hub, Main Hall")
                        .createdAt(OffsetDateTime.now(ZoneOffset.UTC))
                        .updatedAt(OffsetDateTime.now(ZoneOffset.UTC))
                        .build();
                Event savedNetworking = eventService.save(networking);
                createDemoTicketsForEvent(savedNetworking, ticketService, 4);
                
                log.info("Demo events created successfully");
            } else {
                log.info("Events already exist, skipping event creation");
            }
        } catch (Exception e) {
            log.error("Error creating demo events", e);
        }
    }
    
    private void createDemoTicketsForEvent(Event event, TicketServicePort ticketService, int count) {
        log.info("Creating {} tickets for event: {}", count, event.getName());
        
        String[] attendeeNames = {
            "John Smith", "Jane Doe", "Alice Johnson", 
            "Bob Williams", "Carol Davis", "David Miller",
            "Eva Wilson", "Frank Brown", "Grace Taylor"
        };
        
        for (int i = 0; i < count; i++) {
            String ticketCode = generateTicketCode(event);
            String attendeeName = attendeeNames[i % attendeeNames.length];
            
            Ticket ticket = Ticket.builder()
                    .code(ticketCode)
                    .eventId(event.getId())
                    .attendeeName(attendeeName)
                    .status(i % 3 == 0 ? TicketStatus.USED : TicketStatus.ACTIVE) // Mix of used and active tickets
                    .purchaseDate(OffsetDateTime.now(ZoneOffset.UTC).minus(i, ChronoUnit.DAYS))
                    .createdAt(OffsetDateTime.now(ZoneOffset.UTC))
                    .updatedAt(OffsetDateTime.now(ZoneOffset.UTC))
                    .build();
            
            ticketService.save(ticket);
        }
    }
    
    /**
     * Generates a unique ticket code based on the event
     */
    private String generateTicketCode(Event event) {
        // Format: First 3 chars of event name + random UUID part (first 8 chars)
        String eventPrefix = event.getName().replaceAll("\\s+", "").substring(0, Math.min(event.getName().length(), 3)).toUpperCase();
        String uniquePart = UUID.randomUUID().toString().substring(0, 8);
        
        return eventPrefix + "-" + uniquePart;
    }
}