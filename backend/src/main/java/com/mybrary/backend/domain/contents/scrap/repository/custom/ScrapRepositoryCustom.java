package com.mybrary.backend.domain.contents.scrap.repository.custom;

import com.mybrary.backend.domain.contents.scrap.entity.Scrap;
import java.util.List;
import java.util.Optional;

public interface ScrapRepositoryCustom {

      Optional<Integer> getScrapCount(Long paperId);

      Optional<Scrap> getScrap(Long bookId, Long paperId);

}
