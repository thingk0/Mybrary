package com.mybrary.backend.domain.category.repository.custom;

import com.mybrary.backend.domain.category.dto.responseDto.CategoryGetDto;
import com.mybrary.backend.domain.category.dto.responseDto.MyCategoryGetDto;
import java.util.List;
import java.util.Optional;

public interface CategoryRepositoryCustom {

    Optional<List<CategoryGetDto>> getAllCategory(Long bookshelfId);

    Optional<Integer> nextSeq(Long bookShelfId);

    Optional<List<MyCategoryGetDto>> getMyAllCategory(Long memberId);

    Optional<Long> findCategoryOwnerId(Long categoryId);
}
