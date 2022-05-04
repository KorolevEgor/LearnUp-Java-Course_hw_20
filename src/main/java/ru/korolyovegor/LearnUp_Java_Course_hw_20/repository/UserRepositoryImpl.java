package ru.korolyovegor.LearnUp_Java_Course_hw_20.repository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final Map<String, UserDetails> storage;

    public UserRepositoryImpl() {
        storage = new HashMap<>();
        storage.put("user", new User(
                    "user",
                    new BCryptPasswordEncoder().encode("user123"),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))));
        storage.put("admin", new User(
                    "admin",
                    new BCryptPasswordEncoder().encode("admin123"),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))));
    }

    @Override
    public UserDetails get(String username) {
        return storage.get(username);
    }
}
