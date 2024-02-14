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
    private Long chatRoomId;

    public NotificationGetDto(Long notifyId, MemberInfoDto sender, int notifyType, Long bookId, String bookName, Long threadId,
                              Long paperId, Long commentId, Long replyCommentId) {
        this.notifyId = notifyId;
        this.sender = sender;
        this.notifyType = notifyType;
        this.bookId = bookId;
        this.bookName = bookName;
        this.threadId = threadId;
        this.paperId = paperId;
        this.commentId = commentId;
        this.replyCommentId = replyCommentId;
    }
}
