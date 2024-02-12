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
    private String email;
    private String nickname;
    private String content;
    private String profileImageUrl;
    private LocalDateTime timestamp;

    public static ChatMessageResponseDto of(ChatMessage chatMessage, Member member) {
        return ChatMessageResponseDto.builder()
                                     .chatRoomId(chatMessage.getChatRoom().getId())
                                     .chatMessageId(chatMessage.getId())
                                     .senderId(member.getId())
                                     .email(member.getEmail())
                                     .nickname(member.getNickname())
                                     .content(chatMessage.getMessage())
                                     .profileImageUrl(member.getProfileImage().getUrl())
                                     .timestamp(chatMessage.getCreatedAt())
                                     .build();
    }

}
