package com.ticketmanagement.demo.core.domain.exception;

/**
 * Domain specific exceptions for the ticket management system
 */
public class DomainExceptions {
    
    /**
     * Exception thrown when a requested entity is not found
     */
    public static class EntityNotFoundException extends RuntimeException {
        public EntityNotFoundException(String message) {
            super(message);
        }

        public EntityNotFoundException(String entityType, String identifier) {
            super(entityType + " with identifier " + identifier + " not found");
        }
    }
    
    /**
     * Exception thrown when an operation violates business rules
     */
    public static class BusinessRuleViolationException extends RuntimeException {
        public BusinessRuleViolationException(String message) {
            super(message);
        }
    }
    
    /**
     * Exception thrown when access to a resource is not allowed
     */
    public static class AccessDeniedException extends RuntimeException {
        public AccessDeniedException(String message) {
            super(message);
        }
    }
    
    /**
     * Exception thrown when authentication fails
     */
    public static class AuthenticationException extends RuntimeException {
        public AuthenticationException(String message) {
            super(message);
        }
    }
}
