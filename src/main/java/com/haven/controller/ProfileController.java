package com.haven.controller;

import com.haven.model.Profile;
import com.haven.model.User;
import com.haven.service.ProfileService;
import com.haven.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final UserService userService;

    @GetMapping
    public Profile getProfile(@AuthenticationPrincipal String email) {
        if (email == null) {
            throw new RuntimeException("Not authenticated");
        }
        User user = userService.findByEmailOrThrow(email);
        return profileService.getProfile(user.getId());
    }

    @GetMapping("/user/{userId}")
    public Profile getProfileByUserId(@PathVariable Long userId) {
        return profileService.getProfile(userId);
    }

    @PutMapping
    public Profile updateProfile(
            @RequestBody Profile profileData,
            @AuthenticationPrincipal String email
    ) {
        if (email == null) {
            throw new RuntimeException("Not authenticated");
        }
        User user = userService.findByEmailOrThrow(email);
        return profileService.updateProfile(user.getId(), profileData);
    }

    @GetMapping("/status")
    public Map<String, Boolean> getProfileStatus(@AuthenticationPrincipal String email) {
        if (email == null) {
            throw new RuntimeException("Not authenticated");
        }
        User user = userService.findByEmailOrThrow(email);
        boolean completed = profileService.isProfileComplete(user.getId());
        return Map.of("completed", completed);
    }

    @PostMapping("/photo")
    public String uploadPhoto(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal String email
    ) {
        if (email == null) {
            throw new RuntimeException("Not authenticated");
        }
        User user = userService.findByEmailOrThrow(email);
        Profile profile = profileService.getProfile(user.getId());
        return profileService.uploadProfilePhoto(profile.getId(), file);
    }
}
