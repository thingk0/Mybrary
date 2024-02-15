package com.mybrary.backend.domain.chat.repository.impl;

import static com.mybrary.backend.domain.chat.entity.QChatJoin.chatJoin;
import static com.mybrary.backend.domain.chat.entity.QChatMessage.chatMessage;
import static com.mybrary.backend.domain.chat.entity.QChatRoom.chatRoom;
import static com.mybrary.backend.domain.image.entity.QImage.image;
import static com.mybrary.backend.domain.member.entity.QMember.member;

import com.mybrary.backend.domain.chat.dto.responseDto.ChatRoomResponseDto;
import com.mybrary.backend.domain.chat.entity.ChatRoom;
import com.mybrary.backend.domain.chat.entity.QChatJoin;
import com.mybrary.backend.domain.chat.entity.QChatMessage;
import com.mybrary.backend.domain.chat.repository.custom.ChatRoomRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Optional<List<Long>> chatRoomIdList2(Long myId, Pageable page) {

        QChatMessage chatMessage = new QChatMessage("chatMessage");
        QChatMessage chatMessageSub = new QChatMessage("chatMessageSub");

        return Optional.ofNullable(query.select(chatRoom.id)
                                        .from(chatRoom)
                                        .leftJoin(chatJoin).on(chatJoin.chatRoom.id.eq(chatRoom.id))
                                        .leftJoin(chatMessage).on(chatMessage.chatRoom.id.eq(chatRoom.id))
                                        .where(chatJoin.joinMember.id.eq(myId)
                                                                     .and(chatJoin.isExited.eq(false))
                                                                     .and(chatRoom.id.in(
                                                                         JPAExpressions.select(chatMessageSub.chatRoom.id).distinct()
                                                                                       .from(chatMessageSub)
                                                                                       .where(chatMessageSub.chatRoom.id.eq(chatJoin.chatRoom.id)))))
                                        .groupBy(chatRoom.id)
                                        .orderBy(chatMessage.createdAt.max().desc())
                                        .offset(page.getOffset())
                                        .limit(page.getPageSize())
                                        .fetch());

    }

    @Override
    public Page<ChatRoomResponseDto> fetchMyChatRoomList(String email, Pageable pageable) {

        QChatJoin subChatJoin = new QChatJoin("subChatJoin");
        QChatMessage subChatMessage = new QChatMessage("subChatMessage");

        JPQLQuery<Long> chatRoomSubQuery = JPAExpressions
            .select(subChatJoin.chatRoom.id)
            .from(subChatJoin)
            .innerJoin(subChatJoin.joinMember, member)
            .where(member.email.eq(email).and(subChatJoin.isExited.eq(false)));

        JPQLQuery<Long> latestMessageSubQuery = JPAExpressions
            .select(subChatMessage.id.max())
            .from(subChatMessage)
            .where(subChatMessage.chatRoom.id.eq(chatRoom.id))
            .groupBy(subChatMessage.chatRoom.id);

        List<ChatRoomResponseDto> content =
            query.select(Projections.constructor(ChatRoomResponseDto.class,
                                                 chatRoom.id,
                                                 member.id,
                                                 member.email,
                                                 member.nickname,
                                                 member.profileImage.url,
                                                 chatMessage.message,
                                                 chatMessage.sender.id))
                 .from(chatRoom)
                 .innerJoin(chatJoin).on(chatJoin.chatRoom.id.eq(chatRoom.id)).on(chatJoin.chatRoom.id.in(chatRoomSubQuery))
                 .leftJoin(chatJoin.joinMember, member)
                 .leftJoin(member.profileImage, image)
                 .leftJoin(chatMessage).on(chatMessage.chatRoom.id.eq(chatRoom.id)).on(chatMessage.id.eq(latestMessageSubQuery))
                 .where(member.email.ne(email))
                 .orderBy(chatMessage.createdAt.desc())
                 .fetch();

        JPAQuery<ChatRoom> countQuery = query.selectFrom(chatRoom)
                                             .innerJoin(chatJoin).on(chatJoin.chatRoom.id.eq(chatRoom.id)).fetchJoin()
                                             .leftJoin(chatJoin.joinMember, member).fetchJoin()
                                             .where(member.email.ne(email));

        Map<Long, Long> unreadMessageCounts = query
            .select(chatMessage.chatRoom.id, chatMessage.count())
            .from(chatRoom)
            .leftJoin(chatJoin).on(chatJoin.chatRoom.id.eq(chatRoom.id)).fetchJoin()
            .leftJoin(member).on(member.email.eq(email), chatJoin.joinMember.id.eq(member.id)).fetchJoin()
            .leftJoin(chatMessage).on(chatMessage.chatRoom.id.eq(chatRoom.id)).fetchJoin()
            .where(chatMessage.isRead.isFalse())
            .groupBy(chatMessage.chatRoom.id)
            .fetch()
            .stream()
            .collect(Collectors.toMap(
                tuple -> tuple.get(0, Long.class),
                tuple -> tuple.get(1, Long.class)
            ));

        content.forEach(dto -> {
            dto.setUnreadMessageCount(unreadMessageCounts.getOrDefault(dto.getChatRoomId(), 0L).longValue());
        });

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }
}
