package com.haven.controller;

import com.haven.dto.SwipeRequest;
import com.haven.service.SwipeService;
import com.haven.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/swipe")
@RequiredArgsConstructor
public class SwipeController {

    private final SwipeService swipeService;
    private final UserService userService;

    @PostMapping
    public String swipe(@RequestBody SwipeRequest swipeRequest) {

        String email = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Long currentUserId = userService
                .findByEmailOrThrow(email)
                .getId();

        return swipeService.swipe(currentUserId, swipeRequest.swipedUserId(), swipeRequest.liked());
    }
}