package com.mybrary.backend.domain.chat.repository.custom;

import static com.mybrary.backend.domain.chat.entity.QChatJoin.chatJoin;
import static com.mybrary.backend.domain.image.entity.QImage.image;
import static com.mybrary.backend.domain.member.entity.QMember.member;

import com.mybrary.backend.domain.chat.entity.ChatJoin;
import com.mybrary.backend.domain.member.dto.login.MemberInfo;
import com.mybrary.backend.domain.member.dto.responseDto.MemberInfoDto;
import com.mybrary.backend.domain.member.entity.Member;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChatJoinRepositoryImpl implements ChatJoinRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Optional<MemberInfoDto> getJoinMemberByMemberId(Long chatRoomId, Long myId) {
        return Optional.ofNullable(
            query.select(Projections.constructor(MemberInfoDto.class, member.id, member.nickname, member.intro, image.id, image.url))
                 .from(chatJoin)
                 .leftJoin(member).on(chatJoin.joinMember.id.eq(member.id))
                 .leftJoin(image).on(member.profileImage.id.eq(image.id))
                 .where(chatJoin.chatRoom.id.eq(chatRoomId).and(member.id.ne(myId)))
                 .fetchOne());
    }
}
