package com.haven.service;

import com.haven.model.Profile;
import com.haven.model.User;
import com.haven.repository.ProfileRepository;
import com.haven.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

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

    public Profile updateProfile(Long userId, Profile profileData) {
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        if (profileData.getBio() != null) {
            profile.setBio(profileData.getBio());
        }
        if (profileData.getAge() != null) {
            profile.setAge(profileData.getAge());
        }
        if (profileData.getGender() != null) {
            profile.setGender(profileData.getGender());
        }
        if (profileData.getLocation() != null) {
            profile.setLocation(profileData.getLocation());
        }
        if (profileData.getInterests() != null) {
            profile.setInterests(profileData.getInterests());
        }

        // Mark as complete if age and gender are set
        if (profile.getAge() != null && profile.getGender() != null
                && profile.getAge() >= 18) {
            profile.setProfileCompleted(true);
        }

        return profileRepository.save(profile);
    }

    public boolean isProfileComplete(Long userId) {
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        return profile.isProfileCompleted();
    }

    public String uploadProfilePhoto(Long profileId, MultipartFile file) {

        Profile profile = profileRepository.findById(profileId)
                .orElseThrow();

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

        Path uploadPath = Paths.get("uploads");

        try {

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(filename);

            Files.copy(file.getInputStream(), filePath);

            String url = "/uploads/" + filename;

            profile.setPhotoUrl(url);

            profileRepository.save(profile);

            return url;

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload photo");
        }
    }
}