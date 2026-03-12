package com.haven.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "matches",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user1_id", "user2_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user1_id", nullable = false)
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user2_id", nullable = false)
    private User user2;
}