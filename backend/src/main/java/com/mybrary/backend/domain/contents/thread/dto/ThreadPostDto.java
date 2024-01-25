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
public class ThreadPostDto {

    /**
     *  스레드 저장 요청
     *  페이퍼 리스트와
     *  공개여부, 스크랩 허용여부 받기 -> 페이퍼 저장 때 페이퍼마다 적용해야함
     */

    private Long bookId;
    private List<PaperPostDto> paperList;
    private boolean isPaperPublic;
    private boolean isScarpEnable;

}
