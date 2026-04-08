package com.haven.controller;

import com.haven.model.Profile;
import com.haven.model.User;
import com.haven.service.DiscoveryService;
import com.haven.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discover")
@RequiredArgsConstructor
public class DiscoveryController {

    private final DiscoveryService discoveryService;
    private final UserService userService;

    @GetMapping
    public List<Profile> discover(
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) String gender,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        String email = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Long userId = userService.findByEmailOrThrow(email).getId();
        return discoveryService.getDiscoveryFeed(
                userId,
                minAge,
                maxAge,
                gender,
                page,
                size
        );
    }

    @GetMapping("/{userId}")
    public List<Profile> discoverByUserId(
            @PathVariable Long userId,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) String gender,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return discoveryService.getDiscoveryFeed(
                userId,
                minAge,
                maxAge,
                gender,
                page,
                size
        );
    }
}