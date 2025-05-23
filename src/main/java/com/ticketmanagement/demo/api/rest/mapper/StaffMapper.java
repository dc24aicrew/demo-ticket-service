package com.ticketmanagement.demo.api.rest.mapper;

import com.ticketmanagement.demo.api.rest.dto.StaffDTO;
import com.ticketmanagement.demo.core.domain.entity.Staff;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Staff domain entities and DTOs
 */
@Component
public class StaffMapper implements BaseMapper<StaffDTO, Staff> {
    
    @Override
    public StaffDTO toDto(Staff entity) {
        return StaffDTO.builder()
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
    public Staff toEntity(StaffDTO dto) {
        return Staff.builder()
                .id(dto.getId())
                .name(dto.getName())
                .role(dto.getRole())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .department(dto.getDepartment())
                .active(dto.getActive() != null ? dto.getActive() : true)
                .build();
    }
}