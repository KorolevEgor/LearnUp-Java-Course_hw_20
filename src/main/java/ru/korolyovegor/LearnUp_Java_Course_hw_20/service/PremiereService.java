package ru.korolyovegor.LearnUp_Java_Course_hw_20.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.domain.Premiere;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.domain.Ticket;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.dto.PremiereDto;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.dto.TicketDto;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.repository.PremiereRepository;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.repository.TicketRepository;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.entity.PremiereEntity;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.entity.TicketEntity;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PremiereService {

    private final PremiereRepository premiereRepository;
    private final Map<UUID, Premiere> premiereMap = new HashMap<>();

    private final TicketRepository ticketRepository;
    private final Map<UUID, Ticket> ticketMap = new HashMap<>();

    Mapper mapper;

    public PremiereService(@Autowired PremiereRepository premiereRepository
            , @Autowired TicketRepository ticketRepository
            , @Autowired Mapper mapper) {
        this.premiereRepository = premiereRepository;
        this.ticketRepository = ticketRepository;
        this.mapper = mapper;

        for (PremiereEntity premiereEntity : premiereRepository.findAll()) {
            premiereMap.put(premiereEntity.getId(), Premiere.builder()
                    .id(premiereEntity.getId())
                    .name(premiereEntity.getName())
                    .description(premiereEntity.getDescription())
                    .ageCategory(premiereEntity.getAgeCategory())
                    .quantityOfSeats(premiereEntity.getQuantityOfSeats())
                    .seatsUsed(premiereEntity.getSeatsUsed()).build());
        }

        for (TicketEntity ticketEntity : ticketRepository.findAll()) {
            ticketMap.put(ticketEntity.getId(), Ticket.builder()
                    .id(ticketEntity.getId())
                    .place(ticketEntity.getPlace())
                    .premiere(ticketEntity.getPremiere().getId())
                    .build());
        }
    }

    @Transactional(
            timeout = 5
    )
    public void insertPremiere(PremiereDto premiereDto) {
        PremiereEntity premiere = mapper.toEntity(premiereDto);
        premiereRepository.save(premiere);
        premiereMap.put(premiere.getId(), Premiere.builder()
                .id(premiere.getId())
                .name(premiere.getName())
                .description(premiere.getDescription())
                .ageCategory(premiere.getAgeCategory())
                .quantityOfSeats(premiere.getQuantityOfSeats())
                .seatsUsed(premiere.getSeatsUsed()).build());
    }

    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            timeout = 5
    )
    public void updatePremiere(PremiereDto premiereDto) {
        PremiereEntity premiere = mapper.toEntity(premiereDto);
        premiereMap.remove(premiere.getId());
        premiereMap.put(premiere.getId(), Premiere.builder()
                .id(premiere.getId())
                .name(premiere.getName())
                .description(premiere.getDescription())
                .ageCategory(premiere.getAgeCategory())
                .quantityOfSeats(premiere.getQuantityOfSeats())
                .seatsUsed(premiere.getSeatsUsed()).build());

        // добавить проверку на то, что нет ссылок из tickets на запись premiereEntity
        premiereRepository.save(premiere);
    }

    @Transactional(
            isolation = Isolation.REPEATABLE_READ
    )
    public void deletePremiere(UUID id) {
        premiereRepository.deleteById(id);
        premiereMap.remove(id);
    }

    @Transactional(
            isolation = Isolation.READ_COMMITTED,
            readOnly = true
    )
    public void printAllPremieres() {
        premiereRepository.findAll().forEach(System.out::println);
    }

    @Transactional
    public List<PremiereDto> getAllPremieres() {
        return premiereRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public PremiereDto getPremiereById(UUID id) {
        return mapper.toDto(premiereRepository.getById(id));
    }

    @Transactional(
            isolation = Isolation.READ_COMMITTED,
            readOnly = true
    )
    public void readPremiereById(UUID id) {
        System.out.println(premiereRepository.findById(id));
    }

    @Scheduled(cron = "${interval-in-cron-free-seat}")
    public ArrayList<Integer> freeSeat() {
        ArrayList<Integer> result = new ArrayList<>();
        for (PremiereEntity premiere : premiereRepository.findAll()) {
            if (premiereMap.get(premiere.getId()).isFreeSeat()) {
                result.add(premiere.getQuantityOfSeats() - premiere.getSeatsUsed());
            } else {
                result.add(0);
            }
        }
        return result;
    }
}
