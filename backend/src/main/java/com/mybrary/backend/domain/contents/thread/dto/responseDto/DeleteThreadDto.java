package com.mybrary.backend.domain.contents.thread.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteThreadDto {

    /* 삭제된 Paper 개수 */
    private Long paperCount;

    /* 삭제된 thread id */
    private Long threadId;
}
