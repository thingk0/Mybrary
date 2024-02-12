package com.mybrary.backend.domain.comment.dto.responseDto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentGetDto {

    /**
     *  댓글 전체 조회 (List 안에 넣어서 사용) or 댓글 알림 눌렀을 때 필요한 댓글만 조회
     *
     */

//     - 댓글ID(commentId)
//  - 작성자ID(ownerId)
//  - 작성자닉네임(ownerNickname)
//  - 작성자여부(isOwner)
//  - 댓글내용(comment)
//  - 컬러코드(colorCode)
//  - 시간(time)

    private Long commentId;
    private Long ownerId;
    private String ownerNickname;
    private boolean isOwner;
    private String content;
    private int colorCode;
    private int childCommentCount;  //대댓글 개수
    private LocalDateTime createdAt;

    public CommentGetDto(Long commentId, Long ownerId, String ownerNickname,
        String content, int colorCode, LocalDateTime createdAt) {
        this.commentId = commentId;
        this.ownerId = ownerId;
        this.ownerNickname = ownerNickname;
        this.content = content;
        this.colorCode = colorCode;
        this.createdAt = createdAt;
    }

    public void updateIsOwner(boolean bool){
        this.isOwner = bool;
    }
    public void updateChildCommentCount(int count){
        this.childCommentCount = count;
    }
}
