package com.mybrary.backend.domain.category.controller;

import com.mybrary.backend.domain.book.dto.BookGetDto;
import com.mybrary.backend.domain.category.dto.CategoryGetDto;
import com.mybrary.backend.domain.category.dto.CategoryPostDto;
import com.mybrary.backend.domain.category.dto.CategoryUpdateDto;
import com.mybrary.backend.domain.category.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
public class CategoryControllerV1 {

    private final CategoryService categoryService;

    @Operation(summary = "카테고리 조회", description = "책장 아이디를 통한 카테고리 목록 조회")
    @GetMapping
    public ResponseEntity<?> getCategoriesById(
        @RequestParam(name = "bookshelf-id") Long bookshelfId) {

        return new ResponseEntity<List<CategoryGetDto>>(HttpStatus.OK);
    }

    //    이건 Book 도메인 내에 들어가야 하는게 더 어룰리는것 같긴 함
    @Operation(summary = "카테고리 책 리스트 조회", description = "카테고리 아이디를 통한 책 목록 조회")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getBooksByCategoryId(@PathVariable Long categoryId) {

        return new ResponseEntity<List<BookGetDto>>(HttpStatus.OK);
    }

    @Operation(summary = "카테고리 생성", description = "카테고리 생성")
    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryPostDto category) {

        return new ResponseEntity<Long>(HttpStatus.OK);
    }

    @Operation(summary = "카테고리 수정", description = "카테고리 관련 정보 수정")
    @PutMapping
    public ResponseEntity<?> updateCategory(@RequestBody CategoryUpdateDto category) {

        return new ResponseEntity<Long>(HttpStatus.OK);
    }
    @Operation(summary = "카테고리 삭제", description = "카테고리 아이디를 통한 카테고리 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable(name = "id") Long categoryId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }



}
