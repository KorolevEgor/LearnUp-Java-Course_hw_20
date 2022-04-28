package ru.korolyovegor.LearnUp_Java_Course_hw_20.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.domain.Premiere;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.domain.Ticket;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.repository.PremiereRepository;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.repository.TicketRepository;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.entity.PremiereEntity;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.entity.TicketEntity;

import java.util.*;

@Component
public class PremiereService {

    private PremiereRepository premiereRepository;
    Map<UUID, Premiere> premiereMap = new HashMap<>();

    private TicketRepository ticketRepository;
    Map<UUID, Ticket> ticketMap = new HashMap<>();

    public PremiereService(@Autowired PremiereRepository premiereRepository, @Autowired TicketRepository ticketRepository) {
        this.premiereRepository = premiereRepository;
        this.ticketRepository = ticketRepository;

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
                    .premiere(ticketEntity.getPremiere())
                    .build());
        }
    }

//    ArrayList<Premiere> premiereList;

//    public PremiereService() {
//        premiereList = new ArrayList<>();
//    }

    @Transactional(
            timeout = 5
    )
    public void insert(PremiereEntity premiere) {
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
    public void update(PremiereEntity premiere) {
        premiereMap.remove(premiere.getId());
        premiereMap.put(premiere.getId(), Premiere.builder()
                .id(premiere.getId())
                .name(premiere.getName())
                .description(premiere.getDescription())
                .ageCategory(premiere.getAgeCategory())
                .quantityOfSeats(premiere.getQuantityOfSeats())
                .seatsUsed(premiere.getSeatsUsed()).build());
//        premiereRepository.deleteById(premiere.getId());
        premiereRepository.save(premiere);
    }

    @Transactional(
            isolation = Isolation.REPEATABLE_READ
    )
    public void delete(UUID id) {
        premiereRepository.deleteById(id);
        premiereMap.remove(id);
    }

    @Transactional(
            isolation = Isolation.READ_COMMITTED,
            readOnly = true
    )
    public void read() {
        premiereRepository.findAll().forEach(System.out::println);
    }

    @Transactional
    public List<PremiereEntity> getAll() {
        return premiereRepository.findAll();
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
            update(premiereBuy);
            t = TicketEntity.builder()
                    .id(UUID.randomUUID())
                    .premiere(premiereBuy)
                    .place("12A-30").build();
            ticketRepository.save(t);
            ticketMap.remove(t.getId());
            ticketMap.put(t.getId(), Ticket.builder()
                    .id(t.getId())
                    .place(t.getPlace())
                    .premiere(t.getPremiere()).build());
        }
        return t;
    }

    @Transactional
    public void refundTicket(@org.jetbrains.annotations.NotNull PremiereEntity premiereRefund) {
        UUID id = premiereRefund.getId();
        Premiere premiereObj = premiereMap.get(id);

        for (TicketEntity ticket : ticketRepository.findAll()) {
            if (ticket.getPremiere().getId().equals(id)) {
                ticketRepository.deleteById(ticket.getId());
                ticketMap.remove(ticket.getId());
            }
        }
        premiereObj.unband();
        update(premiereRefund);
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
