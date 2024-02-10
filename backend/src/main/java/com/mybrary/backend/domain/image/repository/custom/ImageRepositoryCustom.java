package com.mybrary.backend.domain.image.repository.custom;

import com.mybrary.backend.domain.image.entity.Image;
import java.util.List;
import java.util.Optional;

public interface ImageRepositoryCustom {

    /* 2개만 조회됨을 보장해야함 */
    Optional<List<Long>> findPaperImage(Long paperId);

    Optional<Image> findImage1ByPaperId(Long paperId);

    Optional<Image> findImage2ByPaperId(Long paperId);
}
