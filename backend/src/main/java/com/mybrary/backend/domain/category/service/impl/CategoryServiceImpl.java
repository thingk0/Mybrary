package com.mybrary.backend.domain.category.service.impl;

import com.mybrary.backend.domain.bookshelf.entity.Bookshelf;
import com.mybrary.backend.domain.bookshelf.repository.BookShelfRepository;
import com.mybrary.backend.domain.category.dto.responseDto.CategoryGetDto;
import com.mybrary.backend.domain.category.dto.requestDto.CategoryPostDto;
import com.mybrary.backend.domain.category.dto.requestDto.CategoryUpdateDto;
import com.mybrary.backend.domain.category.entity.Category;
import com.mybrary.backend.domain.category.repository.CategoryRepository;
import com.mybrary.backend.domain.category.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final BookShelfRepository bookShelfRepository;
    @Override
    public List<CategoryGetDto> getAllCategory(Long bookshelfId) {
        return categoryRepository.getAllCategory(bookshelfId);
    }

    @Transactional
    @Override
    public Long createCategory(CategoryPostDto category) {
        Bookshelf bookshelf = bookShelfRepository.findById(category.getBookShelfId()).get();
        int seq = categoryRepository.nextSeq(category.getBookShelfId()).orElse(0);
        Category categoryEntity = Category.builder()
            .categoryName(category.getName())
            .bookshelf(bookshelf)
            .categorySeq(seq+1)
            .build();
        Category savedCategory = categoryRepository.save(categoryEntity);
        return savedCategory.getId();
    }

    @Transactional
    @Override
    public void updateCategory(CategoryUpdateDto category) {
        for (int i = 0;i<category.getCategoryList().size();i++) {
            CategoryGetDto c = category.getCategoryList().get(i);

            Long categoryId = c.getCategoryId();
            Category categoryEntity = categoryRepository.findById(categoryId).get();
            categoryEntity.setCategoryName(c.getName());
            categoryEntity.setCategorySeq(i+1);
        }
    }

    @Transactional
    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).get();
        categoryRepository.delete(category);
    }
}
