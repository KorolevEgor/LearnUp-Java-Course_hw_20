package ru.korolyovegor.LearnUp_Java_Course_hw_20.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.repository.PremiereRepository;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.repository.TicketRepository;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.model.Premiere;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.model.Ticket;

import java.util.ArrayList;
import java.util.UUID;

@Component
public class PremiereService {

    @Autowired
    private PremiereRepository premiereRepository;

    @Autowired
    private TicketRepository ticketRepository;

//    ArrayList<Premiere> premiereList;

//    public PremiereService() {
//        premiereList = new ArrayList<>();
//    }

    @Transactional(
            timeout = 1
    )
    public void insert(Premiere premiere) {
        premiereRepository.save(premiere);
    }

    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            timeout = 1
    )
    public void update(Premiere premiere) {
        premiereRepository.deleteById(premiere.getId());
        premiereRepository.save(premiere);
    }

    @Transactional(
            isolation = Isolation.REPEATABLE_READ
    )
    public void delete(UUID id) {
        premiereRepository.deleteById(id);
    }

    @Transactional(
            isolation = Isolation.READ_COMMITTED,
            readOnly = true
    )
    public void read() {
        premiereRepository.findAll().forEach(System.out::println);
    }

    @Transactional(
            isolation = Isolation.READ_COMMITTED,
            readOnly = true
    )
    public void readById(UUID id) {
        System.out.println(premiereRepository.findById(id));
    }

    @Transactional
    public Ticket buyTicket(@org.jetbrains.annotations.NotNull Premiere premiereBuy) {
        Ticket t = null;
        if (premiereBuy.isFreeSeat()) {
            premiereBuy.book();
            update(premiereBuy);
            t = Ticket.builder()
                    .id(UUID.randomUUID())
                    .premiere(premiereBuy)
                    .place("12A-30").build();
            ticketRepository.save(t);
        }
        return t;
    }

    @Transactional
    public void refundTicket(Premiere premiereRefund) {
        UUID id = premiereRefund.getId();

        for (Ticket ticket : ticketRepository.findAll()) {
            if (ticket.getPremiere().getId().equals(id)) {
                ticketRepository.deleteById(ticket.getId());
            }
        }
        premiereRefund.unband();
        update(premiereRefund);
    }

    @Scheduled(cron = "${interval-in-cron-free-seat}")
    public ArrayList<Integer> freeSeat() {
        ArrayList<Integer> result = new ArrayList<>();
        for (Premiere premiere : premiereRepository.findAll()) {
            if (premiere.isFreeSeat()) {
                result.add(premiere.getQuantityOfSeats() - premiere.getSeatsUsed());
            } else {
                result.add(0);
            }
        }
        return result;
    }
}
