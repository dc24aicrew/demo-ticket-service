package com.ticketmanagement.demo.core.port.spi;

import com.ticketmanagement.demo.core.domain.entity.Staff;

import java.util.List;

/**
 * Repository port for Staff domain entity
 * Extends the base repository port with Staff-specific methods
 */
public interface StaffRepositoryPort extends BaseRepositoryPort<Staff> {
    
    /**
     * Find staff members by department
     * @param department the department to search for
     * @return list of staff members in the specified department
     */
    List<Staff> findByDepartment(String department);
    
    /**
     * Find active staff members
     * @return list of active staff members
     */
    List<Staff> findByActiveTrue();
}