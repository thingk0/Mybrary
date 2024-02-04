package com.mybrary.backend.domain.contents.thread.dto;

import com.mybrary.backend.domain.contents.paper.dto.PaperPostDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThreadUpdateDto {

    /**
     *  스레드 수정 요청
     *
     */

    private Long threadId;
    private Long bookId;
    private List<PaperPostDto> paperList;
    private boolean isPaperPublic;
    private boolean isScarpEnable;

}
