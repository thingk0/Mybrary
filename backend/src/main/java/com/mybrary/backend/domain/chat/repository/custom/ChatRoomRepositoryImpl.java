package com.mybrary.backend.domain.chat.repository.custom;

import static com.mybrary.backend.domain.chat.entity.QChatJoin.chatJoin;
import static com.mybrary.backend.domain.chat.entity.QChatRoom.chatRoom;

import com.mybrary.backend.domain.chat.entity.QChatMessage;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

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
}
