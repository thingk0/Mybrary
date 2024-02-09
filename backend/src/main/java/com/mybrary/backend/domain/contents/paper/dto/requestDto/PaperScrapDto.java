package com.mybrary.backend.domain.contents.paper.dto.requestDto;

import java.util.List;
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
public class PaperScrapDto {

    /**
     *  페이퍼 스크랩 요청
     *  스크랩할 페이퍼 아이디만 리스트로 같이 받기
     */

    private Long bookId;
    private List<Long> paperIdList;

}
