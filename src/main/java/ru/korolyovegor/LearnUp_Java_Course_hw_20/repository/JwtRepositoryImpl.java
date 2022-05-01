package ru.korolyovegor.LearnUp_Java_Course_hw_20.repository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JwtRepositoryImpl implements JwtRepository {
//    @PersistenceContext
//    private EntityManager em;

    private final Map<String, String> storage = new HashMap<>();

    @Override
    public void save(String username, String token) {
        storage.put(username, token);
//        em.createNativeQuery("insert into jwt values (?, ?)")
//                .setParameter(1, username)
//                .setParameter(2, token)
//                .executeUpdate();
    }

    @Override
    public String get(String username) {
        return storage.get(username);
//        Query query = em.createNativeQuery("select username from jwt where token=?");
//        query.setParameter(1, token);
//        return (String) query.getSingleResult();
    }
}
