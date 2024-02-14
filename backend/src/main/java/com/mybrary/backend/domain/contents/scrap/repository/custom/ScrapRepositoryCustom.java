package com.mybrary.backend.domain.contents.scrap.repository.custom;

import com.mybrary.backend.domain.contents.scrap.entity.Scrap;
import java.util.Optional;

public interface ScrapRepositoryCustom {

    Optional<Integer> getScrapCount(Long paperId);

    Optional<Scrap> getScrap(Long bookId, Long paperId);

    Optional<Integer> findLastPaperSeq(Long bookId);

    Optional<Integer> getThreadScrapCount(Long threadId);

}
