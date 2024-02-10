package com.mybrary.backend.domain.image.repository.custom;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.query.Param;

public interface ImageRepositoryCustom {

    /* 2개만 조회됨을 보장해야함 */
    Optional<List<String>> findPaperImage(@Param("paperId") Long paperId);

}
