package com.ticketmanagement.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main Spring Boot application class
 * Component scan ensures all layers of clean architecture are included
 */
@SpringBootApplication
@ComponentScan(basePackages = {
    "com.ticketmanagement.demo.api",
    "com.ticketmanagement.demo.core",
    "com.ticketmanagement.demo.infrastructure"
})
public class TicketManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketManagementApplication.class, args);
    }
}
