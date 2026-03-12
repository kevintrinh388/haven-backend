package com.haven.service;

import com.haven.model.*;
import com.haven.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SwipeService {

    private final SwipeRepository swipeRepository;
    private final MatchRepository matchRepository;
    private final UserRepository userRepository;

    public String swipe(Long swiperId, Long swipedId, boolean liked) {

        if (swiperId.equals(swipedId)) {
            throw new RuntimeException("Cannot swipe yourself");
        }

        User swiper = userRepository.findById(swiperId)
                .orElseThrow(() -> new RuntimeException("Swiper not found"));

        User swiped = userRepository.findById(swipedId)
                .orElseThrow(() -> new RuntimeException("Swiped user not found"));

        // Save swipe
        Swipe swipe = Swipe.builder()
                .swiper(swiper)
                .swiped(swiped)
                .liked(liked)
                .build();

        swipeRepository.save(swipe);

        // If user swiped left, we can just stop here
        if (!liked) {
            return "Swipe recorded (not liked)";
        }

        // Check if other user already liked back
        boolean mutualLike = swipeRepository
                .findBySwiperIdAndSwipedId(swipedId, swiperId)
                .map(Swipe::getLiked)
                .orElse(false);

        if (mutualLike) {

            Match match = Match.builder()
                    .user1(swiper)
                    .user2(swiped)
                    .build();

            matchRepository.save(match);

            return "It's a MATCH!";
        }

        return "Swipe recorded (liked)";
    }

    public List<Match> getMatches(Long userId) {
        return matchRepository.findByUser1IdOrUser2Id(userId, userId);
    }
}