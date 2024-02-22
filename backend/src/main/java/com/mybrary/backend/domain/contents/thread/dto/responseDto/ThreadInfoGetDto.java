package com.mybrary.backend.domain.contents.thread.dto.responseDto;

import java.time.LocalDateTime;
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
    private LocalDateTime threadCreatedAt;
    private Long imageId;
    private String imageUrl;
    private int likesCount;
    private int commentCount;
    private int scrapCount;
    private boolean isPaperPublic;
    private boolean isScrapEnabled;


    public ThreadInfoGetDto(Long threadId, LocalDateTime threadCreatedAt, Long imageId,
        String imageUrl, boolean isPaperPublic, boolean isScrapEnabled) {
        this.threadId = threadId;
        this.threadCreatedAt = threadCreatedAt;
        this.imageId = imageId;
        this.imageUrl = imageUrl;
        this.likesCount = likesCount;
        this.commentCount = commentCount;
        this.scrapCount = scrapCount;
        this.isPaperPublic = isPaperPublic;
        this.isScrapEnabled = isScrapEnabled;
    }


}
