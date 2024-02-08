package com.mybrary.backend.domain.contents.thread.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteThreadDto {

    /* 삭제된 Paper 개수 */
    private Long paperCount;

    /* 삭제된 thread id */
    private Long threadId;
}
