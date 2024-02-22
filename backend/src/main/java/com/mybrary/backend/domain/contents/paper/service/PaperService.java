package com.mybrary.backend.domain.contents.paper.service;

import com.mybrary.backend.domain.contents.paper.dto.requestDto.PaperScrapDto;
import com.mybrary.backend.domain.contents.paper.dto.requestDto.PaperShareDto;
import com.mybrary.backend.domain.contents.paper.dto.responseDto.ToggleLikeResult;

public interface PaperService {

    Long scrapPaper(Long myId, PaperScrapDto paperScrapDto);

    Long sharePaper(PaperShareDto share);

    ToggleLikeResult toggleLike(Long memberId, Long paperId);

}
