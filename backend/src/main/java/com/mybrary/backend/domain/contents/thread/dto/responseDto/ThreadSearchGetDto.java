package com.mybrary.backend.domain.contents.thread.dto.responseDto;

import com.mybrary.backend.domain.member.dto.responseDto.MemberInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThreadSearchGetDto {

    /**
     *  스레드 검색 or pipi에서 공유해서 메세지로 간 스레드 에 사용
     *  스레드의 썸네일과 좋아요, 댓글, 스크랩수만 보이는 간단한 조회
     */

    private Long threadId;
    private MemberInfoDto writer;
    private String thumbnailImageUrl;
    private int likeCount;
    private int commentCount;
    private int scrapCount;

}
