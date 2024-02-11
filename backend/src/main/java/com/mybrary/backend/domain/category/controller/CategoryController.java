package com.mybrary.backend.domain.category.controller;

import com.mybrary.backend.domain.book.dto.responseDto.BookGetDto;
import com.mybrary.backend.domain.book.service.BookService;
import com.mybrary.backend.domain.category.dto.responseDto.CategoryGetDto;
import com.mybrary.backend.domain.category.dto.requestDto.CategoryPostDto;
import com.mybrary.backend.domain.category.dto.requestDto.CategoryUpdateDto;
import com.mybrary.backend.domain.category.service.CategoryService;
import com.mybrary.backend.global.format.code.ApiResponse;
import com.mybrary.backend.global.format.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Category 컨트롤러", description = "Category Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final ApiResponse response;
    private final CategoryService categoryService;
    private final BookService bookService;

    @Operation(summary = "카테고리 조회", description = "책장 아이디를 통한 카테고리 목록 조회")
    @GetMapping
    public ResponseEntity<?> getAllCategoryById(
        @Parameter(hidden = true) Authentication authentication,
        @RequestParam(name = "bookshelfId") Long bookshelfId) {

        List<CategoryGetDto> result = categoryService.getAllCategory(authentication.getName(), bookshelfId);
        return response.success(ResponseCode.CATEGORIES_FETCHED, result);
    }

    @Operation(summary = "카테고리 책 리스트 조회", description = "카테고리 아이디를 통한 책 목록 조회")
    @GetMapping("/{id}")
    public ResponseEntity<?> getAllBookByCategoryId(
        @Parameter(hidden = true) Authentication authentication,
        @PathVariable(name = "id") Long categoryId) {

        List<BookGetDto> result = bookService.getAllBookByCategoryId(authentication.getName(), categoryId);
        return response.success(ResponseCode.CATEGORY_BOOKS_FETCHED, result);
    }

    @Operation(summary = "카테고리 생성", description = "카테고리 생성")
    @PostMapping
    public ResponseEntity<?> createCategory(
        @Parameter(hidden = true) Authentication authentication,
        @RequestBody CategoryPostDto category) {

        Long categoryId = categoryService.createCategory(category);
        return response.success(ResponseCode.CATEGORY_CREATED, categoryId);
    }

    @Operation(summary = "카테고리 수정", description = "카테고리 관련 정보 수정")
    @PutMapping
    public ResponseEntity<?> updateCategory(
        @Parameter(hidden = true) Authentication authentication,
        @RequestBody CategoryUpdateDto category) {

        categoryService.updateCategory(category);
        return response.success(ResponseCode.CATEGORY_UPDATED, category.getBookShelfId());
    }

    @Operation(summary = "카테고리 삭제", description = "카테고리 아이디를 통한 카테고리 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(
        @Parameter(hidden = true) Authentication authentication,
        @PathVariable(name = "id") Long categoryId) {

        categoryService.deleteCategory(categoryId);
        return response.success(ResponseCode.CATEGORY_DELETED);
    }


}
