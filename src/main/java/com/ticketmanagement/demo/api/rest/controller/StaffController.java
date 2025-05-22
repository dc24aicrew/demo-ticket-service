package com.ticketmanagement.demo.api.rest.controller;

import com.ticketmanagement.demo.api.rest.dto.StaffDTO;
import com.ticketmanagement.demo.api.rest.mapper.StaffMapper;
import com.ticketmanagement.demo.core.domain.entity.Staff;
import com.ticketmanagement.demo.core.port.api.StaffServicePort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for Staff management operations
 */
@RestController
@RequestMapping("/api/staffs")
public class StaffController extends BaseController<Staff, StaffDTO> {
    
    private final StaffServicePort staffService;
    private final StaffMapper staffMapper;
    
    public StaffController(StaffServicePort staffService, StaffMapper staffMapper) {
        super(staffService, staffMapper);
        this.staffService = staffService;
        this.staffMapper = staffMapper;
    }
    
    /**
     * Get staff members by department
     * @param department the department to search for
     * @return list of staff members in the specified department
     */
    @GetMapping("/department/{department}")
    public ResponseEntity<List<StaffDTO>> findByDepartment(@PathVariable String department) {
        List<Staff> staffList = staffService.findByDepartment(department);
        return ResponseEntity.ok(staffMapper.toDtoList(staffList));
    }
    
    /**
     * Get active staff members
     * @return list of active staff members
     */
    @GetMapping("/active")
    public ResponseEntity<List<StaffDTO>> findActiveStaff() {
        List<Staff> staffList = staffService.findByActiveTrue();
        return ResponseEntity.ok(staffMapper.toDtoList(staffList));
    }
}