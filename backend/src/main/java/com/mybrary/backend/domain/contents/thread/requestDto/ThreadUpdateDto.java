package com.mybrary.backend.domain.contents.thread.requestDto;

import com.mybrary.backend.domain.contents.paper.dto.PaperUpdateDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThreadUpdateDto {

    /**
     * 스레드 수정 요청
     */

    private Long threadId;
    private boolean isPaperPublic;
    private boolean isScrapEnable;
    private List<PaperUpdateDto> paperList;

}
