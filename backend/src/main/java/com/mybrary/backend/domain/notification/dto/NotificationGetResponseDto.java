package com.mybrary.backend.domain.notification.dto;

import com.mybrary.backend.domain.member.dto.MemberResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationGetResponseDto {

    private Long notifyId;
    private MemberResponseDto receiver;
    private int notifyType;
    private Long bookId;
    private String bookName;
    private Long threadId;
    private Long paperId;
    private Long commentId;
    private Long recommentId;

}
