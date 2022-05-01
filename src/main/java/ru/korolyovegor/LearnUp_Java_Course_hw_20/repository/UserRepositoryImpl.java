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

    private final Map<String, UserDetails> storage = new HashMap<>() {{
            put("user", new User(
                    "user",
                    new BCryptPasswordEncoder().encode("user123"),
                    Collections.singletonList(new SimpleGrantedAuthority("USER"))));
    }};

    @Override
    public UserDetails get(String username) {
        return storage.get(username);
    }
}
