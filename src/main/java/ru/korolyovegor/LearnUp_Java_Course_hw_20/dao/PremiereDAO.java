package ru.korolyovegor.LearnUp_Java_Course_hw_20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.model.Premiere;

import java.util.UUID;

public interface PremiereDAO extends JpaRepository<Premiere, UUID> {
}
