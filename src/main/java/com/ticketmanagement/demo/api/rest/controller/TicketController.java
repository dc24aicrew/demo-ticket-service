package com.ticketmanagement.demo.api.rest.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ticketmanagement.demo.api.rest.dto.TicketDto;
import com.ticketmanagement.demo.api.rest.dto.TicketStatusUpdateDto;
import com.ticketmanagement.demo.api.rest.mapper.TicketMapper;
import com.ticketmanagement.demo.core.domain.entity.Ticket;
import com.ticketmanagement.demo.core.domain.entity.TicketStatus;
import com.ticketmanagement.demo.core.port.api.TicketServicePort;

import jakarta.validation.Valid;

/**
 * REST controller for ticket operations
 */
@RestController
@RequestMapping("/tickets")
public class TicketController extends BaseController<Ticket, TicketDto> {

    private final TicketServicePort ticketService;
    private final TicketMapper ticketMapper;

    public TicketController(TicketServicePort ticketService, TicketMapper ticketMapper) {
        super(ticketService, ticketMapper);
        this.ticketService = ticketService;
        this.ticketMapper = ticketMapper;
    }

    /**
     * Search for tickets by various criteria
     *
     * @param code Optional ticket code
     * @param eventId Optional event ID
     * @param attendeeName Optional attendee name
     * @param status Optional ticket status
     * @return List of tickets matching the search criteria
     */
    @GetMapping("/search")
    public ResponseEntity<List<TicketDto>> searchTickets(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) UUID eventId,
            @RequestParam(required = false) String attendeeName,
            @RequestParam(required = false) String status) {

        List<Ticket> tickets;

        // Prioritize search criteria
        if (code != null && !code.isEmpty()) {
            return ticketService.findByCode(code)
                    .map(ticket -> ResponseEntity.ok(List.of(ticketMapper.toDto(ticket))))
                    .orElse(ResponseEntity.ok(List.of()));
        } else if (eventId != null) {
            tickets = ticketService.findByEventId(eventId);
        } else if (attendeeName != null && !attendeeName.isEmpty()) {
            tickets = ticketService.findByAttendeeName(attendeeName);
        } else if (status != null && !status.isEmpty()) {
            try {
                TicketStatus ticketStatus = TicketStatus.valueOf(status.toUpperCase());
                tickets = ticketService.findByStatus(ticketStatus);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().build();
            }
        } else {
            tickets = ticketService.findAll();
        }

        return ResponseEntity.ok(ticketMapper.toDtoList(tickets));
    }

    /**
     * Update ticket status
     *
     * @param id Ticket ID
     * @param statusUpdateDto Status update DTO
     * @return Updated ticket
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<TicketDto> updateTicketStatus(
            @PathVariable UUID id,
            @Valid @RequestBody TicketStatusUpdateDto statusUpdateDto) {

        try {
            TicketStatus newStatus = TicketStatus.valueOf(statusUpdateDto.getStatus().toUpperCase());
            Ticket updatedTicket = ticketService.updateStatus(id, newStatus);
            return ResponseEntity.ok(ticketMapper.toDto(updatedTicket));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).build();
        }
    }
}