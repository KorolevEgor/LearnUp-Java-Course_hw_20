package ru.korolyovegor.LearnUp_Java_Course_hw_20.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.model.Premiere;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.model.Ticket;

import java.util.ArrayList;
import java.util.UUID;

@Component
public class PremiereService {

    ArrayList<Premiere> premiereList;

    public PremiereService() {
        premiereList = new ArrayList<>();
    }

    public void insert(Premiere premiere) {
        if (!premiereList.contains(premiere))
            premiereList.add(premiere);
    }

    public void update(Premiere premiere) {
        for (int i = 0; i < premiereList.size(); i++) {
            if (premiereList.get(i).getId().equals(premiere.getId())) {
                premiereList.set(i, premiere);
                break;
            }
        }
    }

    public void delete(UUID id) {
        for (int i = 0; i < premiereList.size(); i++) {
            if (premiereList.get(i).getId().equals(id)) {
                premiereList.remove(i);
                break;
            }
        }
    }

    public void read() {
        premiereList.forEach(System.out::println);
    }

    public void readById(UUID id) {
        for (Premiere premiere : premiereList) {
            if (premiere.getId().equals(id)) {
                System.out.println(premiere);
            }
        }
    }

    public Ticket buyTicket(@org.jetbrains.annotations.NotNull Premiere premiereBuy) {
        UUID id = premiereBuy.getId();
        for (Premiere premiere : premiereList) {
            if (premiere.getId().equals(id)) {
                if (premiere.isFreeSeat()) {
                    premiere.book();
                    return Ticket.builder()
                            .id(UUID.randomUUID())
                            .premiere(premiereBuy)
                            .place("12A-30").build();
                }
            }
        }
        return null;
    }

    public void refundTicket(Premiere premiereRefund) {
        UUID id = premiereRefund.getId();
        for (Premiere premiere : premiereList) {
            if (premiere.getId().equals(id)) {
                if (premiere.isFreeSeat()) {
                    premiere.unband();
                    return;
                }
            }
        }
    }

    @Scheduled(cron = "${interval-in-cron-free-seat}")
    public ArrayList<Integer> freeSeat() {
        ArrayList<Integer> result = new ArrayList<>();
        for (Premiere premiere : premiereList) {
            if (premiere.isFreeSeat()) {
                result.add(premiere.getQuantityOfSeats() - premiere.getSeatsUsed());
            } else {
                result.add(0);
            }
        }
        return result;
    }
}
