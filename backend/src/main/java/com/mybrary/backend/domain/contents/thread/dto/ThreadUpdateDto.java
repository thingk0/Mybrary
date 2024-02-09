package com.mybrary.backend.domain.contents.thread.dto;

import com.mybrary.backend.domain.contents.paper.dto.PaperUpdateDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThreadUpdateDto {

    /**
     * 스레드 수정 요청
     */

    private Long memberId;
    private Long threadId;
    private List<PaperUpdateDto> paperList;

}
