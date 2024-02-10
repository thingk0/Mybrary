package com.mybrary.backend.domain.contents.thread.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThreadInfoGetDto {

    /* 내 thread 조회, 특정 멤버의 thread 조회에 사용 */

    private Long threadId;
    private Long imageId;
    private String imageUrl;
    private int likesCount;
    private int commentCount;
    private int scrapCount;

}
