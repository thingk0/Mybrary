package com.mybrary.backend.domain.contents.paper.dto.responseDto;

import lombok.Builder;

@Builder
public class ToggleLikeResult {
    /* 좋아요 또는 좋아요 취소에 따른 반환값을 문자열 true / false로 반환 */

    /* 좋아요가 되었으면 "true" 리턴, 좋아요 취소가 되었다면 "false" 리턴 */
    private String likeResult;
    private Long paperId;
}
