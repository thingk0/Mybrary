package com.mybrary.backend.domain.contents.paper.service;

import com.mybrary.backend.domain.contents.paper.dto.PaperScrapDto;
import com.mybrary.backend.domain.contents.paper.dto.PaperShareDto;
import com.mybrary.backend.domain.contents.paper.dto.ToggleLikeResult;

public interface PaperService {

    Long scrapPaper(PaperScrapDto paperScrapDto);

    Long sharePaper(PaperShareDto share);

    ToggleLikeResult toggleLike(Long memberId, Long paperId);

}
