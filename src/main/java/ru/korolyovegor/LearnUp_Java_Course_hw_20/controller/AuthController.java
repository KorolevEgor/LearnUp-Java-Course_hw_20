package ru.korolyovegor.LearnUp_Java_Course_hw_20.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.korolyovegor.LearnUp_Java_Course_hw_20.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private UserService userService;

    @PostMapping("/auth")
    public void auth(HttpServletRequest request, HttpServletResponse response) {

    }


}
