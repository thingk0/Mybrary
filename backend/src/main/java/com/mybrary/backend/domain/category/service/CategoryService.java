package com.mybrary.backend.domain.category.service;

import com.mybrary.backend.domain.category.dto.CategoryGetDto;
import com.mybrary.backend.domain.category.dto.CategoryPostDto;
import com.mybrary.backend.domain.category.dto.CategoryUpdateDto;
import java.util.List;

public interface CategoryService {

    List<CategoryGetDto> getAllCategory(Long bookshelfId);

    Long createCategory(CategoryPostDto category);

    void updateCategory(CategoryUpdateDto category);

    void deleteCategory(Long categoryId);
}
