package com.ticketmanagement.demo.core.usecase;

import com.ticketmanagement.demo.core.domain.entity.Staff;
import com.ticketmanagement.demo.core.port.api.StaffServicePort;
import com.ticketmanagement.demo.core.port.spi.StaffRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Staff service implementation that provides business logic for staff operations
 */
@Service
public class StaffService extends BaseService<Staff> implements StaffServicePort {
    
    private final StaffRepositoryPort staffRepository;
    
    public StaffService(StaffRepositoryPort staffRepository) {
        super(staffRepository);
        this.staffRepository = staffRepository;
    }
    
    @Override
    public List<Staff> findByDepartment(String department) {
        return staffRepository.findByDepartment(department);
    }
    
    @Override
    public List<Staff> findByActiveTrue() {
        return staffRepository.findByActiveTrue();
    }
}