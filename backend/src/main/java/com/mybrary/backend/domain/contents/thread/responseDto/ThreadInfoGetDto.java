package com.mybrary.backend.domain.contents.thread.responseDto;

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
public class ThreadInfoGetDto {

    /* 내 thread 조회, 특정 멤버의 thread 조회에 사용 */

    private Long threadId;
    private String imageUrl;
    private int likesCount;
    private int commentCount;
    private int scrapCount;
    private boolean isPaperPublic;
    private boolean isScrapEnable;
}
