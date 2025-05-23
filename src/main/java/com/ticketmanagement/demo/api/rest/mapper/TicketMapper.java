package com.ticketmanagement.demo.api.rest.mapper;

import com.ticketmanagement.demo.api.rest.dto.TicketDto;
import com.ticketmanagement.demo.core.domain.entity.Ticket;
import com.ticketmanagement.demo.core.domain.entity.TicketStatus;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Ticket entity and TicketDTO
 */
@Component
public class TicketMapper implements BaseMapper<TicketDto, Ticket> {
    
    @Override
    public TicketDto toDto(Ticket entity) {
        if (entity == null) {
            return null;
        }
        
        return TicketDto.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .eventId(entity.getEventId())
                .attendeeName(entity.getAttendeeName())
                .status(entity.getStatus() != null ? entity.getStatus().name() : null)
                .purchaseDate(entity.getPurchaseDate())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
    
    @Override
    public Ticket toEntity(TicketDto dto) {
        if (dto == null) {
            return null;
        }
        
        return Ticket.builder()
                .id(dto.getId())
                .code(dto.getCode())
                .eventId(dto.getEventId())
                .attendeeName(dto.getAttendeeName())
                .status(dto.getStatus() != null ? TicketStatus.valueOf(dto.getStatus()) : null)
                .purchaseDate(dto.getPurchaseDate())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }
}