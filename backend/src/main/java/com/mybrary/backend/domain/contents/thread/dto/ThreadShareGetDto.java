package com.mybrary.backend.domain.contents.thread.dto;

import com.mybrary.backend.domain.member.dto.MemberInfoDto;
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
public class ThreadShareGetDto {

    /**
     *  pipi에서 공유해서 메세지로 간 스레드 에 사용
     *
     */

    private Long threadId;
    private String thumbnailImageUrl;
    private Long writerId;
    private String writerNickname;
    private String writerProfileUrl;

    public ThreadShareGetDto(Long threadId) {
        this.threadId = threadId;
    }
}
