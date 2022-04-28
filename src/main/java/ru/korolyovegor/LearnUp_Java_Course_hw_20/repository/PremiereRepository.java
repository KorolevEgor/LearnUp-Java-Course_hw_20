package ru.korolyovegor.LearnUp_Java_Course_hw_20.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.model.Premiere;

import java.util.UUID;

public interface PremiereRepository extends JpaRepository<Premiere, UUID> {
}
