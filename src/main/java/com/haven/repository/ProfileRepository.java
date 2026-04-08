package com.haven.repository;

import com.haven.model.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByUserId(Long userId);

    @Query("""
            SELECT p FROM Profile p
            WHERE p.user.id != :userId
            AND (p.profileCompleted = true)
            AND (:gender IS NULL OR p.gender = :gender)
            AND (:minAge IS NULL OR p.age >= :minAge)
            AND (:maxAge IS NULL OR p.age <= :maxAge)
            AND p.user.id NOT IN (
                SELECT s.swiped.id FROM Swipe s WHERE s.swiper.id = :userId
            )
            AND p.user.id NOT IN (
                SELECT m.user1.id FROM Match m WHERE m.user2.id = :userId
            )
            AND p.user.id NOT IN (
                SELECT m.user2.id FROM Match m WHERE m.user1.id = :userId
            )
            """)
    List<Profile> findProfilesForDiscovery(
            Long userId,
            Integer minAge,
            Integer maxAge,
            String gender,
            Pageable pageable
    );
}