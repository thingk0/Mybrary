package com.mybrary.backend.domain.chat.repository.custom;

import static com.mybrary.backend.domain.chat.entity.QChatJoin.chatJoin;
import static com.mybrary.backend.domain.member.entity.QMember.member;

import com.mybrary.backend.domain.chat.entity.ChatJoin;
import com.mybrary.backend.domain.member.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChatJoinRepositoryImpl implements ChatJoinRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Member getJoinMemberByMemberId(Long chatRoomId, Long myId) {
        return query.select(member)
            .from(chatJoin)
            .leftJoin(member).on(chatJoin.joinMember.id.eq(member.id))
            .where(chatJoin.chatRoom.id.eq(chatRoomId).and(member.id.ne(myId)))
            .fetchOne();
    }
}
