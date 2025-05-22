package com.ticketmanagement.demo.infrastructure.persistence.repository;

import com.ticketmanagement.demo.infrastructure.persistence.entity.StaffJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA repository for Staff entities
 * Provides database operations for Staff records
 */
@Repository
public interface StaffJpaRepository extends BaseJpaRepository<StaffJpaEntity> {
    
    /**
     * Find staff members by department
     * @param department the department to search for
     * @return list of staff members in the specified department
     */
    List<StaffJpaEntity> findByDepartment(String department);
    
    /**
     * Find active staff members
     * @return list of active staff members
     */
    List<StaffJpaEntity> findByActiveTrue();
}