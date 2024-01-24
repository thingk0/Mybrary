package com.mybrary.backend.domain.category.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Category 컨트롤러", description = "Category Controller API")
@RestController
@RequestMapping("/api/v1/category")
public class CategoryControllerV1 {

    @Operation(summary = "카테고리 조회", description = "책장 아이디를 통한 카테고리 목록 조회")
    @GetMapping
    public ResponseEntity<?> getCategoriesById(
        @RequestParam(name = "bookshelf-id") Long bookshelfId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //    이건 Book 도메인 내에 들어가야 하는게 더 어룰리는것 같긴 함
    @Operation(summary = "책 조회", description = "카테고리 아이디를 통한 책 목록 조회")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getBooksByCategoryId(@PathVariable Long categoryId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "카테고리 생성", description = "카테고리 생성")
    @PostMapping
    public ResponseEntity<?> createCategory() {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "카테고리 수정", description = "카테고리 관련 정보 수정")
    @PutMapping
    public ResponseEntity<?> updateCategory() {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "카테고리 삭제", description = "카테고리 아이디를 통한 카테고리 삭제")
    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
