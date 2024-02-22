package com.mybrary.backend.domain.chat.repository.custom;

import com.mybrary.backend.domain.member.entity.Member;
import java.util.Optional;

public interface ChatJoinRepositoryCustom {

    Optional<Member> findOtherMemberInChatRoom(Long chatRoomId, Long myId);

    boolean isValidChatJoiner(Long chatRoomId, String email);

}
