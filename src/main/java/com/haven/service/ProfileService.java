package com.haven.service;

import com.haven.model.Profile;
import com.haven.model.User;
import com.haven.repository.ProfileRepository;
import com.haven.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public Profile createProfile(Long userId, Profile profileData) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Profile profile = Profile.builder()
                .bio(profileData.getBio())
                .age(profileData.getAge())
                .location(profileData.getLocation())
                .interests(profileData.getInterests())
                .user(user)
                .build();

        return profileRepository.save(profile);
    }

    public Profile getProfile(Long userId) {
        return profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
    }
}