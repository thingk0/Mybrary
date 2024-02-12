package com.mybrary.backend.domain.chat.entity;

import com.mybrary.backend.domain.base.BaseEntity;
import com.mybrary.backend.domain.chat.dto.requestDto.MessageRequestDto;
import com.mybrary.backend.domain.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE chat_message SET is_deleted = TRUE WHERE chat_message_id = ?")
public class ChatMessage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    @Column(name = "message")
    private String message;

    @Column(name = "thread_id")
    private Long threadId;

    @Setter
    @Column(name = "is_read")
    private boolean isRead;

    public static ChatMessage of(MessageRequestDto requestDto, ChatRoom chatRoom, Member sender) {
        return ChatMessage.builder()
                          .chatRoom(chatRoom)
                          .sender(sender)
                          .message(requestDto.getMessage())
                          .threadId(requestDto.getThreadId())
                          .build();
    }

}
