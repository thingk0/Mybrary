package com.mybrary.backend.domain.comment.dto.requestDto;

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
public class CommentPostDto {

    /**
     *  댓글 작성 요청
     *  대댓글 아니면 parentCommentId는 null
     */

    private Long paperId;
    private Long parentCommentId;
    private String content;
    private int colorCode;

}
