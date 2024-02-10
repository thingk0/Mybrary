package com.mybrary.backend.domain.chat.repository.custom;

import com.mybrary.backend.domain.member.entity.Member;
import java.util.Optional;

public interface ChatJoinRepositoryCustom {

    Optional<Member> getJoinMemberByMemberId(Long chatRoomId, Long myId);


}
