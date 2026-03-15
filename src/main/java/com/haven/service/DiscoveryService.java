package com.haven.service;

import com.haven.model.Profile;
import com.haven.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DiscoveryService {

    private final ProfileRepository profileRepository;

    public List<Profile> getDiscoveryFeed(Long userId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        return profileRepository.findProfilesForDiscovery(userId, pageable);
    }
}