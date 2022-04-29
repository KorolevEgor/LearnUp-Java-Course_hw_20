package ru.korolyovegor.LearnUp_Java_Course_hw_20.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.dto.TicketDto;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.service.PremiereService;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    PremiereService premiereService;

    @Autowired
    public TicketController(PremiereService premiereService) {
        this.premiereService = premiereService;
    }

    @GetMapping
    public Collection<TicketDto> getAllTickets() {
        return premiereService.getAllTickets();
    }

    @GetMapping("/{id}")
    public TicketDto getTicket(@PathVariable("id") UUID id) {
        return premiereService.getTicketById(id);
    }

    @PostMapping
    public void insertTicket(@RequestBody TicketDto ticketDto) {
        premiereService.insertTicket(ticketDto);
    }

    @PutMapping("/{id}")
    public void updateTicket(@RequestBody TicketDto ticketDto, @PathVariable("id") UUID id) {
        premiereService.updateTicket(ticketDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable("id") UUID id) {
        premiereService.deletePremiere(id);
    }
}
