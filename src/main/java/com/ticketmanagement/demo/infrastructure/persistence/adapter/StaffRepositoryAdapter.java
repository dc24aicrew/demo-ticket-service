package com.ticketmanagement.demo.infrastructure.persistence.adapter;

import com.ticketmanagement.demo.core.domain.entity.Staff;
import com.ticketmanagement.demo.core.port.spi.StaffRepositoryPort;
import com.ticketmanagement.demo.infrastructure.persistence.entity.StaffJpaEntity;
import com.ticketmanagement.demo.infrastructure.persistence.repository.StaffJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Repository adapter for Staff entities
 * Maps between domain entities and JPA entities
 */
@Component
public class StaffRepositoryAdapter extends BaseRepositoryAdapter<Staff, StaffJpaEntity> implements StaffRepositoryPort {
    
    private final StaffJpaRepository staffJpaRepository;
    
    public StaffRepositoryAdapter(StaffJpaRepository staffJpaRepository) {
        super(staffJpaRepository);
        this.staffJpaRepository = staffJpaRepository;
    }
    
    @Override
    protected Staff mapToEntity(StaffJpaEntity jpaEntity) {
        return Staff.builder()
                .id(jpaEntity.getId())
                .name(jpaEntity.getName())
                .role(jpaEntity.getRole())
                .email(jpaEntity.getEmail())
                .phone(jpaEntity.getPhone())
                .department(jpaEntity.getDepartment())
                .active(jpaEntity.isActive())
                .build();
    }
    
    @Override
    protected StaffJpaEntity mapToJpaEntity(Staff entity) {
        return StaffJpaEntity.builder()
                .id(entity.getId())
                .name(entity.getName())
                .role(entity.getRole())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .department(entity.getDepartment())
                .active(entity.isActive())
                .build();
    }
    
    @Override
    public List<Staff> findByDepartment(String department) {
        return staffJpaRepository.findByDepartment(department).stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Staff> findByActiveTrue() {
        return staffJpaRepository.findByActiveTrue().stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }
}