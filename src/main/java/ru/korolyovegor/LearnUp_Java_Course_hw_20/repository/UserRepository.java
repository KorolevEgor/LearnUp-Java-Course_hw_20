package ru.korolyovegor.LearnUp_Java_Course_hw_20.repository;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository {

    UserDetails get(String token);
}
