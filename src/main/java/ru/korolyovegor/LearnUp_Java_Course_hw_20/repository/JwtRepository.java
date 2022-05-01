package ru.korolyovegor.LearnUp_Java_Course_hw_20.repository;

public interface JwtRepository {

    void save(String username, String token);

    String get(String username);


}
