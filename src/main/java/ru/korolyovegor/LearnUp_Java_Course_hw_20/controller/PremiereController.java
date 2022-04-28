package ru.korolyovegor.LearnUp_Java_Course_hw_20.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.dto.PremiereDto;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.service.Mapper;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.service.PremiereService;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/premieres")
public class PremiereController {

    PremiereService premiereService;
    Mapper mapper;

    @Autowired
    public PremiereController(PremiereService premiereService, Mapper mapper) {
        this.premiereService = premiereService;
        this.mapper = mapper;
    }

    @GetMapping
    public Collection<PremiereDto> getAllPremieres() {
        return premiereService.getAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
