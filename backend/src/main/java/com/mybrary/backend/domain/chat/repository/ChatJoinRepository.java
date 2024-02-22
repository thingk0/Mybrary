package com.mybrary.backend.domain.chat.repository;

import com.mybrary.backend.domain.chat.entity.ChatJoin;
import com.mybrary.backend.domain.chat.repository.custom.ChatJoinRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatJoinRepository extends JpaRepository<ChatJoin, Long>, ChatJoinRepositoryCustom {

    @Query("select cj from ChatJoin cj where cj.joinMember.id = :myId and cj.chatRoom.id = :chatRoomId")
    ChatJoin findByChatJoin(@Param("myId") Long myId, @Param("chatRoomId") Long chatRoomId);
}
