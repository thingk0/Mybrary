package com.mybrary.backend.domain.category.controller;

import com.mybrary.backend.domain.book.dto.BookGetDto;
import com.mybrary.backend.domain.category.dto.CategoryGetDto;
import com.mybrary.backend.domain.category.dto.CategoryPostDto;
import com.mybrary.backend.domain.category.dto.CategoryUpdateDto;
import com.mybrary.backend.domain.category.service.CategoryService;
import com.mybrary.backend.domain.member.dto.MemberInfoDto;
import com.mybrary.backend.global.format.code.ApiResponse;
import com.mybrary.backend.global.format.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @Operation(summary = "카테고리 조회", description = "책장 아이디를 통한 카테고리 목록 조회")
    @GetMapping
    public ResponseEntity<?> getAllCategoryById(
        @RequestParam(name = "bookshelf-id") Long bookshelfId) {

        CategoryGetDto category1 = new CategoryGetDto(1L, "여행", 1, 5);
        CategoryGetDto category2 = new CategoryGetDto(2L, "공부", 3, 3);
        CategoryGetDto category3 = new CategoryGetDto(3L, "일상", 2, 17);
        CategoryGetDto category4 = new CategoryGetDto(4L, "만화", 4, 20);

        List<CategoryGetDto> list = new ArrayList<>();
        list.add(category1);
        list.add(category2);
        list.add(category3);
        list.add(category4);

        return response.success(ResponseCode.CATEGORIES_FETCHED.getMessage(), list);
    }

    @Operation(summary = "카테고리 책 리스트 조회", description = "카테고리 아이디를 통한 책 목록 조회")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getAllBookByCategoryId(@PathVariable Long categoryId) {

        MemberInfoDto writer1 = new MemberInfoDto(1L, "wnsgh", "안녕하세요 최준호입니다", "123123");
        MemberInfoDto writer2 = new MemberInfoDto(2L, "aksrl", "안녕하세요 서만기입니다", "666666");
        MemberInfoDto writer3 = new MemberInfoDto(3L, "gPtjs", "안녕하세요 박혜선입니다", "145643");
        MemberInfoDto writer4 = new MemberInfoDto(4L, "thdud", "안녕하세요 최소영입니다", "000000");

        BookGetDto book1 = new BookGetDto(1L, writer1, "준호의 그림일기", "1234", 2, 4, 0);
        BookGetDto book2 = new BookGetDto(2L, writer2, "만기의 지각", "1234", 1, 1, 2);
        BookGetDto book3 = new BookGetDto(3L, writer3, "혜선이의 여행", "1234", 4, 2, 1);
        BookGetDto book4 = new BookGetDto(4L, writer4, "소영이의 일상", "1234", 3, 1, 0);

        List<BookGetDto> list = new ArrayList<>();
        list.add(book1);
        list.add(book2);
        list.add(book3);
        list.add(book4);

        return response.success(ResponseCode.CATEGORY_BOOKS_FETCHED.getMessage(), list);
    }

    @Operation(summary = "카테고리 생성", description = "카테고리 생성")
    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryPostDto category) {

        return response.success(ResponseCode.CATEGORY_CREATED.getMessage(), category.getName());
    }

    @Operation(summary = "카테고리 수정", description = "카테고리 관련 정보 수정")
    @PutMapping
    public ResponseEntity<?> updateCategory(@RequestBody CategoryUpdateDto category) {

        return response.success(ResponseCode.CATEGORY_UPDATED.getMessage(), category.getBookShelfId());
    }

    @Operation(summary = "카테고리 삭제", description = "카테고리 아이디를 통한 카테고리 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable(name = "id") Long categoryId) {

        return response.success(ResponseCode.CATEGORY_DELETED.getMessage(), categoryId);
    }


}
