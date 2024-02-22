package com.mybrary.backend.domain.chat.dto.responseDto;


import com.mybrary.backend.domain.chat.entity.ChatMessage;
import com.mybrary.backend.domain.member.entity.Member;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessageResponseDto {

    private Long chatRoomId;
    private Long chatMessageId;
    private Long senderId;
    private Long receiverId;
    private String email;
    private String nickname;
    private String content;
    private String profileImageUrl;
    private LocalDateTime timestamp;

    public static ChatMessageResponseDto of(ChatMessage chatMessage, Member sender) {
        return ChatMessageResponseDto.builder()
                                     .chatRoomId(chatMessage.getChatRoom().getId())
                                     .chatMessageId(chatMessage.getId())
                                     .senderId(sender.getId())
                                     .receiverId(chatMessage.getReceiver().getId())
                                     .email(sender.getEmail())
                                     .nickname(sender.getNickname())
                                     .content(chatMessage.getMessage())
                                     .profileImageUrl(sender.getProfileImage().getUrl())
                                     .timestamp(chatMessage.getCreatedAt())
                                     .build();
    }

}
