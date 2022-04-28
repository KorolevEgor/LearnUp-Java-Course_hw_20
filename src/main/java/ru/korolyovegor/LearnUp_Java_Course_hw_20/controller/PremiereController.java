package ru.korolyovegor.LearnUp_Java_Course_hw_20.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.dto.PremiereDto;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.service.PremiereService;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/premieres")
public class PremiereController {

    PremiereService premiereService;

    @Autowired
    public PremiereController(PremiereService premiereService) {
        this.premiereService = premiereService;
    }

    @GetMapping
    public Collection<PremiereDto> getAllPremieres() {
        return premiereService.getAll();
    }

    @GetMapping("/{id}")
    public PremiereDto getPremiere(@PathVariable("id") UUID id) {
        return premiereService.getById(id);
    }

    @PostMapping
    public void insertPremiere(@RequestBody PremiereDto premiereDto) {
        premiereService.insert(premiereDto);
    }

    @PutMapping("/{id}")
    public void updatePremiere(@RequestBody PremiereDto premiereDto, @PathVariable("id") UUID id) {
        premiereService.update(premiereDto);
    }

    @DeleteMapping("/{id}")
    public void deletePremiere(@PathVariable("id") UUID id) {
        premiereService.delete(id);
    }
}
