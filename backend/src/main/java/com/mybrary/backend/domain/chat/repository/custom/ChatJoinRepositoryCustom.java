package com.mybrary.backend.domain.chat.repository.custom;

import com.mybrary.backend.domain.chat.dto.ChatMessageGetDto;
import com.mybrary.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatJoinRepositoryCustom {

    Member getJoinMemberByMemberId(Long chatRoomId, Long myId);


}
