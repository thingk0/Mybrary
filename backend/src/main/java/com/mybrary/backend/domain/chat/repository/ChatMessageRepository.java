package com.mybrary.backend.domain.chat.repository;

import com.mybrary.backend.domain.chat.entity.ChatJoin;
import com.mybrary.backend.domain.chat.entity.ChatMessage;
import com.mybrary.backend.domain.chat.repository.custom.ChatMessageRepositoryCustom;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>, ChatMessageRepositoryCustom {

    /* 채팅 메세지 리스트 조회 (채팅방id로) */
    @Query("select cm from ChatMessage cm where cm.chatRoom.id = :chatRoomId")
    List<ChatMessage> getAllChatMessageByChatRoomId(@Param("chatRoomId") Long chatRoomId, Pageable page);

    /* 채팅 메세지 리스트 조회 (회원id로) */
    @Query("select cj1 from ChatJoin cj1 left join ChatJoin cj2 on cj1.chatRoom.id = cj2.chatRoom.id where cj1.joinMember.id = :myId and cj2.joinMember.id = :memberId")
    ChatJoin isExistChatRoom(@Param("myId") Long myId, @Param("memberId") Long memberId);


}
