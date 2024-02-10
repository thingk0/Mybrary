package com.mybrary.backend.domain.chat.repository.custom;

import com.mybrary.backend.domain.member.dto.login.MemberInfo;
import com.mybrary.backend.domain.member.dto.responseDto.MemberInfoDto;
import com.mybrary.backend.domain.member.entity.Member;
import java.util.Optional;

public interface ChatJoinRepositoryCustom {

    Optional<MemberInfoDto> getJoinMemberByMemberId(Long chatRoomId, Long myId);

}
