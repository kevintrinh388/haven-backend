package com.haven.repository;

import com.haven.model.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByMatchIdOrderByTimestampAsc(Long matchId, Pageable pageable);

}