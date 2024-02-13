package com.mybrary.backend.domain.chat.repository.impl;

import static com.mybrary.backend.domain.chat.entity.QChatJoin.chatJoin;
import static com.mybrary.backend.domain.member.entity.QMember.member;

import com.mybrary.backend.domain.chat.repository.custom.ChatJoinRepositoryCustom;
import com.mybrary.backend.domain.member.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChatJoinRepositoryImpl implements ChatJoinRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Optional<Member> findOtherMemberInChatRoom(Long chatRoomId, Long senderId) {
        return Optional.ofNullable(query
                                       .select(member)
                                       .from(chatJoin)
                                       .leftJoin(member).on(chatJoin.joinMember.id.eq(member.id)).fetchJoin()
                                       .where(chatJoin.chatRoom.id.eq(chatRoomId)
                                                                  .and(member.id.ne(senderId)))
                                       .fetchOne());
    }

    @Override
    public boolean isValidChatJoiner(Long chatRoomId, String email) {
        return query.select(chatJoin)
                    .from(chatJoin)
                    .leftJoin(member).on(chatJoin.joinMember.id.eq(member.id)).fetchJoin()
                    .where(chatJoin.chatRoom.id.eq(chatRoomId).and(member.email.eq(email)))
                    .fetchCount() > 0;
    }
}
