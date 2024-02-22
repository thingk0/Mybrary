package com.mybrary.backend.domain.chat.repository.impl;

import static com.mybrary.backend.domain.chat.entity.QChatMessage.chatMessage;
import static com.mybrary.backend.domain.chat.entity.QChatRoom.chatRoom;
import static com.mybrary.backend.domain.image.entity.QImage.image;
import static com.mybrary.backend.domain.member.entity.QMember.member;

import com.mybrary.backend.domain.chat.dto.responseDto.ChatMessageResponseDto;
import com.mybrary.backend.domain.chat.entity.ChatMessage;
import com.mybrary.backend.domain.chat.repository.custom.ChatMessageRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory query;

    @Override
    public Page<ChatMessageResponseDto> getAllMessagesFromChatRoom(Long chatRoomId, Pageable pageable) {
        List<ChatMessageResponseDto> content =
            query.select(Projections.constructor(ChatMessageResponseDto.class,
                                                 chatRoom.id,
                                                 chatMessage.id,
                                                 member.id,
                                                 chatMessage.receiver.id,
                                                 member.email,
                                                 member.nickname,
                                                 chatMessage.message,
                                                 image.url,
                                                 chatMessage.createdAt
                 ))
                 .from(chatRoom)
                 .leftJoin(chatMessage).on(chatMessage.chatRoom.id.eq(chatRoom.id))
                 .leftJoin(member).on(chatMessage.sender.id.eq(member.id))
                 .leftJoin(image).on(member.profileImage.id.eq(image.id))
                 .where(chatRoom.id.eq(chatRoomId))
                 .limit(100)
                 .offset(pageable.getOffset())
                 .orderBy(chatMessage.createdAt.desc())
                 .fetch();

        JPAQuery<ChatMessage> countQuery = query.select(chatMessage)
                                                .from(chatRoom)
                                                .leftJoin(chatMessage).on(chatMessage.chatRoom.id.eq(chatRoom.id))
                                                .where(chatRoom.id.eq(chatRoomId));

        long updatedCount = query.update(chatMessage)
                                 .set(chatMessage.isRead, true)
                                 .where(chatMessage.chatRoom.id.eq(chatRoomId)
                                                               .and(chatMessage.isRead.isFalse()))
                                 .execute();

        em.flush();
        em.clear();

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }
}
