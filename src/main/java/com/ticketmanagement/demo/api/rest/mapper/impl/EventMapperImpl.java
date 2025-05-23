package com.ticketmanagement.demo.api.rest.mapper.impl;

import com.ticketmanagement.demo.api.rest.dto.EventDto;
import com.ticketmanagement.demo.api.rest.mapper.EventMapper;
import com.ticketmanagement.demo.core.domain.entity.Event;
import org.springframework.stereotype.Component;

/**
 * Implementation of EventMapper interface for converting between Event entity and EventDTO
 */
@Component
public class EventMapperImpl implements EventMapper {
    
    @Override
    public EventDto toDto(Event entity) {
        if (entity == null) {
            return null;
        }
        
        return EventDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .date(entity.getDate())
                .venue(entity.getVenue())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
    
    @Override
    public Event toEntity(EventDto dto) {
        if (dto == null) {
            return null;
        }
        
        return Event.builder()
                .id(dto.getId())
                .name(dto.getName())
                .date(dto.getDate())
                .venue(dto.getVenue())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }
}