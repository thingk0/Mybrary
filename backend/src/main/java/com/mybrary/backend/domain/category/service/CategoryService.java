package com.mybrary.backend.domain.category.service;

import com.mybrary.backend.domain.category.dto.responseDto.CategoryGetDto;
import com.mybrary.backend.domain.category.dto.requestDto.CategoryPostDto;
import com.mybrary.backend.domain.category.dto.requestDto.CategoryUpdateDto;
import java.util.List;

public interface CategoryService {

    List<CategoryGetDto> getAllCategory(String email, Long bookshelfId);

    Long createCategory(CategoryPostDto category);

    void updateCategory(CategoryUpdateDto category);

    void deleteCategory(Long categoryId);
}
