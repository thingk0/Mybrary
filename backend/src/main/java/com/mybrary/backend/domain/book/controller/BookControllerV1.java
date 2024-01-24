package com.mybrary.backend.domain.book.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Book 컨트롤러", description = "Book Controller API")
@RestController
@RequestMapping("/api/v1/book")
public class BookControllerV1 {

    @Operation(summary = "나의 책 목록 조회", description = "나의 책 목록 조회")
    @GetMapping
    public ResponseEntity<?> getBooks() {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "책 생성", description = "책 생성")
    @PostMapping
    public ResponseEntity<?> createBook() {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "북마크 생성", description = "책에 대한 북마크 정보 생성")
    @PostMapping("{id}/bookmark")
    public ResponseEntity<?> createBookmarker(@PathVariable(name = "id") Long bookId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "책 정보 조회", description = "책 아이디를 통한 책 정보 조회")
    @GetMapping("/{id}")
    public ResponseEntity<?> getBook(@PathVariable(name = "id") Long bookId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Operation(summary = "책 수정", description = "책 아이디를 통한 책 정보 수정")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable(name = "id") Long bookId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "책 삭제", description = "책 아이디를 통한 책 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable(name = "id") Long bookId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "책 구독", description = "책 아이디, 카테고리 아이디를 통한 책 구독")
    @PostMapping("/{id}/subscription")
    public ResponseEntity<?> subscribeBook(@PathVariable(name = "id") Long bookId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "책 구독 삭제", description = "책 아이디를 통한 책 구독 취소")
    @DeleteMapping("/{id}/unsubscription")
    public ResponseEntity<?> unsubscribeBook(@PathVariable(name = "id") Long bookId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
