package com.mybrary.backend.domain.comment.dto;

import java.util.List;
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
public class CommentGetDto {

    /**
     *  댓글 전체 조회 (List 안에 넣어서 사용) or 댓글 알림 눌렀을 때 필요한 댓글만 조회
     *
     */

    private Long commentId;
    private String time;
    private Long memberId;
    private String nickname;
    private boolean isOwner;
    private String content;
    private int colorCode;
    private List<Long> mentionIdList;
    private List<CommentGetDto> reCommentList;

}
