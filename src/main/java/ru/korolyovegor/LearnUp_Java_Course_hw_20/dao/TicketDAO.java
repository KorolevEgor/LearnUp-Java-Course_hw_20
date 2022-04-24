package ru.korolyovegor.LearnUp_Java_Course_hw_20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.model.Ticket;

import java.util.UUID;

public interface TicketDAO extends JpaRepository<Ticket, UUID> {
}
