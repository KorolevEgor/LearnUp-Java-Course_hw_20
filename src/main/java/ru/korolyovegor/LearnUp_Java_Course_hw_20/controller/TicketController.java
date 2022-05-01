package ru.korolyovegor.LearnUp_Java_Course_hw_20.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.dto.TicketDto;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.service.PremiereService;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.service.TicketService;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public Collection<TicketDto> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @GetMapping("/{id}")
    public TicketDto getTicket(@PathVariable("id") UUID id) {
        return ticketService.getTicketById(id);
    }

    @PostMapping
    public void insertTicket(@RequestBody TicketDto ticketDto) {
        ticketService.insertTicket(ticketDto);
    }

    @PutMapping("/{id}")
    public void updateTicket(@RequestBody TicketDto ticketDto, @PathVariable("id") UUID id) {
        ticketService.updateTicket(ticketDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable("id") UUID id) {
        ticketService.deleteTicket(id);
    }
}
