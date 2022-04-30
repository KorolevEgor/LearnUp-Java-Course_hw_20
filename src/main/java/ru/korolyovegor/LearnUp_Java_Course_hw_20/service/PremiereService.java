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
            timeout = 5
    )
    public void insertTicket(TicketDto ticketDto) {
        PremiereEntity premiereEntity = premiereRepository.findById(ticketDto.getPremiereId()).get();
        TicketEntity ticket = mapper.toEntity(ticketDto, premiereRepository);
        ticketRepository.save(ticket);
        ticketMap.put(ticket.getId(), Ticket.builder()
                .id(ticket.getId())
                .place(ticket.getPlace())
                .premiere(ticket.getPremiere().getId()).build());

        premiereEntity.setSeatsUsed(premiereEntity.getSeatsUsed() + 1);
        premiereRepository.save(premiereEntity);
        Premiere premiere = premiereMap.get(premiereEntity.getId());
        premiere.setSeatsUsed(premiere.getSeatsUsed() + 1);
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
            isolation = Isolation.REPEATABLE_READ,
            timeout = 5
    )
    public void updateTicket(TicketDto ticketDto) {
        TicketEntity ticket = mapper.toEntity(ticketDto, premiereRepository);
        ticketMap.remove(ticket.getId());
        ticketMap.put(ticket.getId(), Ticket.builder()
                .id(ticket.getId())
                .place(ticket.getPlace())
                .premiere(ticket.getPremiere().getId()).build());

        // добавить проверку на то, что нет ссылок из tickets на запись premiereEntity
        ticketRepository.save(ticket);
    }

    @Transactional(
            isolation = Isolation.REPEATABLE_READ
    )
    public void deletePremiere(UUID id) {
        premiereRepository.deleteById(id);
        premiereMap.remove(id);
    }

    @Transactional(
            isolation = Isolation.REPEATABLE_READ
    )
    public void deleteTicket(UUID id) {
        TicketEntity ticketEntity = ticketRepository.findById(id).get();
        PremiereEntity premiereEntity = premiereRepository.getById(ticketEntity.getPremiere().getId());
        premiereEntity.setSeatsUsed(premiereEntity.getSeatsUsed() - 1);
        premiereRepository.save(premiereEntity);
        Premiere premiere = premiereMap.get(premiereEntity.getId());
        premiere.setSeatsUsed(premiere.getSeatsUsed() - 1);

        ticketRepository.deleteById(id);
        ticketMap.remove(id);
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
    public List<TicketDto> getAllTickets() {
        List<TicketDto> ticketDtoList = new ArrayList<>();
        for (TicketEntity ticket : ticketRepository.findAll()) {
            ticketDtoList.add(mapper.toDto(ticket, ticketRepository));
        }
        return ticketDtoList;
    }

    @Transactional
    public PremiereDto getPremiereById(UUID id) {
        return mapper.toDto(premiereRepository.getById(id));
    }

    @Transactional
    public TicketDto getTicketById(UUID id) {
        TicketEntity ticketEntity = ticketRepository.findById(id).get();
        return mapper.toDto(ticketEntity, ticketRepository);
    }

    @Transactional(
            isolation = Isolation.READ_COMMITTED,
            readOnly = true
    )
    public void readById(UUID id) {
        System.out.println(premiereRepository.findById(id));
    }

    @Transactional
    public TicketEntity buyTicket(@org.jetbrains.annotations.NotNull PremiereEntity premiereBuy) {
        TicketEntity t = null;
        Premiere premiereObj = premiereMap.get(premiereBuy.getId());
        if (premiereObj.isFreeSeat()) {
            premiereObj.book();
            updatePremiere(mapper.toDto(premiereBuy));
            t = TicketEntity.builder()
                    .id(UUID.randomUUID())
                    .premiere(premiereBuy)
                    .place("12A-30").build();
            ticketRepository.save(t);
            ticketMap.remove(t.getId());
            ticketMap.put(t.getId(), Ticket.builder()
                    .id(t.getId())
                    .place(t.getPlace())
                    .premiere(t.getPremiere().getId()).build());
            PremiereEntity premiereEntity = premiereRepository.getById(premiereObj.getId());
            premiereEntity.setSeatsUsed(premiereEntity.getSeatsUsed() + 1);
            premiereRepository.save(premiereEntity);
            premiereObj.setSeatsUsed(premiereObj.getSeatsUsed() + 1);
        }
        return t;
    }

    @Transactional
    public void refundTicket(@org.jetbrains.annotations.NotNull PremiereEntity premiereRefund) {
        UUID id = premiereRefund.getId();
        Premiere premiereObj = premiereMap.get(id);

        for (TicketEntity ticket : ticketRepository.findAll()) {
            if (ticket.getPremiere().equals(id)) {
                ticketRepository.deleteById(ticket.getId());
                ticketMap.remove(ticket.getId());
            }
        }
        premiereObj.unband();
        updatePremiere(mapper.toDto(premiereRefund));
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
