package com.haven.repository;

import com.haven.model.Swipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SwipeRepository extends JpaRepository<Swipe, Long> {

    Optional<Swipe> findBySwiperIdAndSwipedId(Long swiperId, Long swipedId);
}