package com.mybrary.backend.domain.contents.paper.service;

import com.mybrary.backend.domain.contents.paper.dto.PaperScrapDto;
import com.mybrary.backend.domain.contents.paper.dto.PaperShareDto;

public interface PaperService {

    Long scrapPaper(PaperScrapDto paperScrapDto);

    Long sharePaper(PaperShareDto share);

    boolean toggleLike(Long memberId, Long paperId);
    /* dto로 반환하도록 수정할것 */
}
