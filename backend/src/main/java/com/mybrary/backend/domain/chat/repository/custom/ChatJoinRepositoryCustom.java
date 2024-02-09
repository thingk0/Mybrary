package com.mybrary.backend.domain.chat.repository.custom;

import com.mybrary.backend.domain.member.entity.Member;

public interface ChatJoinRepositoryCustom {

    Member getJoinMemberByMemberId(Long chatRoomId, Long myId);


}
