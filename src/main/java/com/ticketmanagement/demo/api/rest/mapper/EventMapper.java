package com.ticketmanagement.demo.api.rest.mapper;

import com.ticketmanagement.demo.api.rest.dto.EventDto;
import com.ticketmanagement.demo.core.domain.entity.Event;

/**
 * Mapper for converting between Event entity and EventDTO
 */
public interface EventMapper extends BaseMapper<EventDto, Event> {
}