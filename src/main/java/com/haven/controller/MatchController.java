package com.haven.controller;

import com.haven.model.Match;
import com.haven.service.SwipeService;
import com.haven.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/matches")
@RequiredArgsConstructor
public class MatchController {

    private final SwipeService swipeService;
    private final UserService userService;

    @GetMapping
    public List<Match> getMatches() {

        String email = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Long userId = userService
                .findByEmailOrThrow(email)
                .getId();

        return swipeService.getMatches(userId);
    }
}