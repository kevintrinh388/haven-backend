package com.haven.controller;

import com.haven.model.Profile;
import com.haven.model.User;
import com.haven.security.JwtAuthenticationToken;
import com.haven.service.ProfileService;
import com.haven.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final UserService userService;

    @PostMapping
    public Profile createProfile(@RequestBody Profile profileData) {

        String email = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User user = userService.findByEmailOrThrow(email);

        return profileService.createProfile(user.getId(), profileData);
    }

    @GetMapping
    public Profile getProfile() {

        String email = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User user = userService.findByEmailOrThrow(email);

        return profileService.getProfile(user.getId());
    }
}