package com.haven.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "swipes",
        // This unique constraint is to prevent duplicate swipes
        uniqueConstraints = @UniqueConstraint(columnNames = {"swiper_id", "swiped_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Swipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "swiper_id", nullable = false)
    private User swiper;

    @ManyToOne
    @JoinColumn(name = "swiped_id", nullable = false)
    private User swiped;

    private Boolean liked;
}