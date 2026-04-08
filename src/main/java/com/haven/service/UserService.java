package com.haven.service;

import com.haven.model.Profile;
import com.haven.model.User;
import com.haven.repository.ProfileRepository;
import com.haven.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public User registerUser(String name, String email, String rawPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = User.builder()
                .name(name)
                .email(email)
                .password(encodedPassword)
                .role("USER")
                .build();
        user = userRepository.save(user);
        Profile profile = Profile.builder()
                .user(user)
                .bio("")
                .age(25)  // Default age - user can edit later
                .gender("other")  // Default - user can edit later
                .location("")
                .interests("")
                .build();
        profileRepository.save(profile);
        return user;
    }

    public User findByEmailOrThrow(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}