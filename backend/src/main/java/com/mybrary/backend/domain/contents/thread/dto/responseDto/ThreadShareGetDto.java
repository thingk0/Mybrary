package com.mybrary.backend.domain.contents.thread.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThreadShareGetDto {

    /**
     *  pipi에서 공유해서 메세지로 간 스레드 에 사용
     *
     */

    private Long threadId;
    private Long threadImageId;
    private String thumbnailImageUrl;
    private Long writerId;
    private String writerNickname;
    private Long writerProfileImageId;
    private String writerProfileUrl;

    public ThreadShareGetDto(Long threadId) {
        this.threadId = threadId;
    }
}
