package com.mybrary.backend.domain.chat.repository;

import com.mybrary.backend.domain.chat.entity.ChatMessage;
import com.mybrary.backend.domain.chat.entity.ChatRoom;
import com.mybrary.backend.domain.chat.repository.custom.ChatRoomRepositoryCustom;
import com.mybrary.backend.domain.member.entity.Member;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, ChatRoomRepositoryCustom {

    /* 채팅방 목록 조회 */
    @Query("select cj.chatRoom.id from ChatJoin cj where cj.joinMember.id = :memberId and cj.isExited = false and cj.chatRoom.id in (select distinct cm.chatRoom.id from ChatMessage cm where cm.chatRoom.id = cj.chatRoom.id)")
    List<Long> chatRoomIdList(@Param("memberId") Long memberId, Pageable page);

    @Query("select m from Member m inner join ChatJoin cj on m.id = cj.joinMember.id where cj.chatRoom.id = :chatRoomId and m.id != :memberId")
    Member findJoinMemberByChatRoomId(@Param("memberId") Long memberId, @Param("chatRoomId") Long chatRoomId);

    @Query("select cm from ChatMessage cm where cm.chatRoom.id = :chatRoomId order by cm.createdAt desc limit 1")
    ChatMessage findRecentMessage(@Param("chatRoomId") Long chatRoomId);

    @Query("select count(*) from ChatMessage cm where cm.chatRoom.id = :chatRoomId")
    int countNotReadMessage(@Param("chatRoomId") Long chatRoomId);

    @Query("select cr from ChatRoom cr inner join ChatJoin cj1 on cr.id = cj1.chatRoom.id inner join ChatJoin cj2 on cj1.chatRoom.id = cj2.chatRoom.id where cj1.joinMember.id = :myId and cj2.joinMember.id = :memberId")
    ChatRoom findChatRoom(@Param("myId") Long myId, @Param("memberId") Long receiverId);
}
