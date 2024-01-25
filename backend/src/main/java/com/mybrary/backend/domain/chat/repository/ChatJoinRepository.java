package com.mybrary.backend.domain.chat.repository;

import com.mybrary.backend.domain.chat.entity.ChatJoin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatJoinRepository extends JpaRepository<ChatJoin, Long> {
}
