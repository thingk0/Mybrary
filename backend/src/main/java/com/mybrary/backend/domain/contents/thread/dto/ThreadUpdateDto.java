package com.mybrary.backend.domain.contents.thread.dto;

import com.mybrary.backend.domain.contents.paper.dto.PaperPostDto;
import com.mybrary.backend.domain.contents.paper.dto.PaperUpdateDto;
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

    private Long memberId;
    private Long threadId;
    private List<PaperUpdateDto> paperList;

}
