package com.mybrary.backend.domain.notification.dto;

import com.mybrary.backend.domain.member.dto.responseDto.MemberInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationGetDto {

    /**
     *  알림 조회
     *  List로 반환
     */

    private Long notifyId;
    private MemberInfoDto sender;
    private int notifyType;
    private Long bookId;
    private String bookName;
    private Long threadId;
    private Long paperId;
    private Long commentId;
    private Long replyCommentId;

}
