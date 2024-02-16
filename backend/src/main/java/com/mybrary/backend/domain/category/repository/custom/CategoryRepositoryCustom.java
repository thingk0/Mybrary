package com.mybrary.backend.domain.category.repository.custom;

import com.mybrary.backend.domain.category.dto.CategoryGetDto;
import com.mybrary.backend.domain.mybrary.dto.MybraryGetDto;
import com.mybrary.backend.domain.mybrary.dto.MybraryOtherGetDto;
import java.util.List;
import java.util.Optional;

public interface CategoryRepositoryCustom {

    List<CategoryGetDto> getAllCategory(Long bookshelfId);

    Optional<Integer> nextSeq(Long bookShelfId);
}
