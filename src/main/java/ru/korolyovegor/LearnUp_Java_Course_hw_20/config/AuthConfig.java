package ru.korolyovegor.LearnUp_Java_Course_hw_20.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.filter.AuthFilter;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.service.JwtService;

@EnableWebSecurity
public class AuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtService jwtService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        AuthFilter authFilter = new AuthFilter(authenticationManager(), jwtService);
        authFilter.setFilterProcessesUrl("/api/auth");

        http
                .csrf().disable()
                .formLogin()
                .loginProcessingUrl("/api/auth").and()
                .authorizeRequests()
                .antMatchers("/sec").authenticated()
                .antMatchers("/api/auth").permitAll()
                .and()
                .addFilter(authFilter);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
