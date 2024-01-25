package com.mybrary.backend.domain.book.controller;

import com.mybrary.backend.domain.book.dto.BookPostDto;
import com.mybrary.backend.domain.book.dto.BookSubscribeDto;
import com.mybrary.backend.domain.book.dto.BookUpdateDto;
import com.mybrary.backend.domain.book.service.BookService;
import com.mybrary.backend.domain.bookmarker.dto.BookMarkerPostDto;
import com.mybrary.backend.domain.category.dto.MyCategoryGetDto;
import com.mybrary.backend.domain.contents.paper.dto.PaperInBookGetDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Book 컨트롤러", description = "Book Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book")
public class BookControllerV1 {

    private final BookService bookService;

    @Operation(summary = "나의 책 목록 조회", description = "나의 책 목록 조회")
    @GetMapping("/my")
    public ResponseEntity<?> getBooks() {

        return new ResponseEntity<List<MyCategoryGetDto>>(HttpStatus.OK);
    }

    @Operation(summary = "책 생성", description = "책 생성")
    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody BookPostDto book) {

        return new ResponseEntity<Long>(HttpStatus.OK);
    }

    @Operation(summary = "북마크 생성", description = "책에 대한 북마크 정보 생성 or 수정")
    @PostMapping("/bookmark")
    public ResponseEntity<?> createBookMarker(@RequestBody BookMarkerPostDto bookMarker) {

        return new ResponseEntity<Long>(HttpStatus.OK);
    }

    @Operation(summary = "책 정보 조회", description = "책 아이디를 통한 책 정보 조회")
    @GetMapping("/{id}")
    public ResponseEntity<?> getBook(@PathVariable(name = "id") Long bookId) {

        return new ResponseEntity<List<PaperInBookGetDto>>(HttpStatus.OK);
    }

    @Operation(summary = "책 수정", description = "책 아이디를 통한 책 정보 수정")
    @PutMapping
    public ResponseEntity<?> updateBook(@RequestBody BookUpdateDto book) {

        return new ResponseEntity<Long>(HttpStatus.OK);
    }

    @Operation(summary = "책 삭제", description = "책 아이디를 통한 책 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable(name = "id") Long bookId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "책 구독", description = "책 아이디, 카테고리 아이디를 통한 책 구독")
    @PostMapping("/subscription")
    public ResponseEntity<?> subscribeBook(@RequestBody BookSubscribeDto bookSubscribe) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "책 구독 삭제", description = "책 아이디를 통한 책 구독 취소")
    @DeleteMapping("/unsubscription/{id}")
    public ResponseEntity<?> unsubscribeBook(@PathVariable(name = "id") Long bookId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
