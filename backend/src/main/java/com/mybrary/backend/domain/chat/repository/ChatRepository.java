package com.mybrary.backend.domain.chat.repository;

import com.mybrary.backend.domain.chat.entity.ChatJoin;
import com.mybrary.backend.domain.chat.entity.ChatMessage;
import com.mybrary.backend.domain.chat.repository.custom.ChatRepositoryCustom;
import com.mybrary.backend.domain.member.entity.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatRepository extends JpaRepository<ChatJoin, Long>,
    ChatRepositoryCustom {

    @Query("select cj.chatRoom.id from ChatJoin cj where cj.joinMember.id = :memberId and cj.isDeleted = false")
    List<Long> chatRoomIdList(@Param("memberId") Long memberId);

    @Query("select m from Member m inner join ChatJoin cj on m.id = cj.joinMember.id where cj.chatRoom.id = :chatRoomId and m.id != :memberId")
    Member findJoinMemberByChatRoomId(@Param("memberId") Long memberId, @Param("chatRoomId") Long chatRoomId);

    @Query("select cm from ChatMessage cm where cm.chatRoom.id = :chatRoomId order by cm.createdAt desc limit 1")
    ChatMessage findRecentMessage(@Param("chatRoomId") Long chatRoomId);

    @Query("select count(*) from ChatMessage cm where cm.chatRoom.id = :chatRoomId")
    int countNotReadMessage(@Param("chatRoomId") Long chatRoomId);

    @Query("select cm from ChatMessage cm where cm.chatRoom.id = :chatRoomId")
    List<ChatMessage> getAllChatMessageByChatRoomId(Long chatRoomId);

    @Query("select m from Member m where m.id = :memberId")
    Member getJoinMemberByMemberId(@Param("memberId") Long memberId);
}
