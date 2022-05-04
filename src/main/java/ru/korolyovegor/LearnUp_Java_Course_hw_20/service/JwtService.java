package ru.korolyovegor.LearnUp_Java_Course_hw_20.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.repository.JwtRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtService {
    public static final long ACCESS_TIMEOUT = 900L;
    public static final long REFRESH_TIMEOUT = 3600L * 24 * 30;

    private final String issuer;
    private final Algorithm algo;
    private final JwtRepository jwtRepository;
    private final JWTVerifier verifier;

    @Autowired
    public JwtService(JwtRepository jwtRepository
            , @Value("${my-config.issuer:MyService}")String issuer
            , @Value("${my-config.secret:123}") String secret) {
        this.jwtRepository = jwtRepository;
        this.issuer = issuer;
        this.algo = Algorithm.HMAC256(secret);
        this.verifier = JWT.require(algo).build();
    }

    public String generateAccessToken(UserDetails user) {
        final List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuer(issuer)
                .withClaim("roles", roles)
                .withExpiresAt(
                        new Date(System.currentTimeMillis() + ACCESS_TIMEOUT * 1000)
                )
                .sign(algo);
    }

    public String generateRefreshToken(UserDetails user) {
        String newToken = JWT.create()
                .withSubject(user.getUsername())
                .withIssuer(issuer)
                .withExpiresAt(
                        new Date(System.currentTimeMillis() + REFRESH_TIMEOUT * 1000)
                )
                .sign(algo);

        jwtRepository.save(user.getUsername(), newToken);

        return newToken;
    }

    public boolean validateRefreshToken(String jwt) {
        try {
            final DecodedJWT decodedJWT = verifier.verify(jwt);
            final String subject = decodedJWT.getSubject();
            final String oldToken = jwtRepository.get(subject);

            return jwt.equals(oldToken);
        } catch (Exception err) {
            return false;
        }
    }

    public boolean verify(String token) {
        try {
            final DecodedJWT decodedJWT = verifier.verify(token);
            final String[] roleNames = decodedJWT.getClaim("roles").asArray(String.class);

            final String username = decodedJWT.getSubject();
            List<SimpleGrantedAuthority> roles = Arrays.stream(roleNames)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            final SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(
                    new UsernamePasswordAuthenticationToken(username, null, roles)
            );
            return true;
        } catch (Exception err) {
            return false;
        }
    }
}
