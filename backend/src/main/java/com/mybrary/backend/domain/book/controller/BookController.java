package com.mybrary.backend.domain.book.controller;

import com.mybrary.backend.domain.book.dto.requestDto.BookPostDto;
import com.mybrary.backend.domain.book.dto.requestDto.BookSubscribeDto;
import com.mybrary.backend.domain.book.dto.requestDto.BookUpdateDto;
import com.mybrary.backend.domain.book.dto.responseDto.BookPaperGetDto;
import com.mybrary.backend.domain.book.service.BookService;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.member.service.MemberService;
import com.mybrary.backend.global.format.code.ApiResponse;
import com.mybrary.backend.global.format.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book 컨트롤러", description = "Book Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book")
public class BookController {

    private final ApiResponse response;
    private final BookService bookService;
    private final MemberService memberService;

    @Operation(summary = "나의 책 목록 조회", description = "나의 책 목록 조회")
    @GetMapping("/my")
    public ResponseEntity<?> getMyBookList(
        @Parameter(hidden = true) Authentication authentication) {

        Member me = memberService.findMember(authentication.getName());
        Long myId = me.getId();
        return response.success(ResponseCode.BOOK_LIST_FETCHED, bookService.getMyBookList(myId));
    }

    @Operation(summary = "책 생성", description = "책 생성")
    @PostMapping
    public ResponseEntity<?> createBook(
        @Parameter(hidden = true) Authentication authentication,
        @RequestBody BookPostDto bookPostDto) {

        Long bookId = bookService.createBook(authentication.getName(), bookPostDto);
        return response.success(ResponseCode.BOOK_CREATED, bookId);
    }

    @Operation(summary = "책 정보 조회(페이퍼리스트 포함)", description = "책 아이디를 통한 책 정보 조회")
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookInfo(
        @Parameter(hidden = true) Authentication authentication,
        @PathVariable(name = "id") Long bookId) {

        BookPaperGetDto book = bookService.getBookInfo(authentication.getName(), bookId);
        return response.success(ResponseCode.BOOK_INFO_FETCHED, book);
    }

    @Operation(summary = "책 수정", description = "책 아이디를 통한 책 정보 수정")
    @PutMapping
    public ResponseEntity<?> updateBook(
        @Parameter(hidden = true) Authentication authentication,
        @RequestBody BookUpdateDto bookUpdateDto) {

        Long bookId = bookService.updateBook(authentication.getName(), bookUpdateDto);
        return response.success(ResponseCode.BOOK_UPDATED, bookId);
    }

    @Operation(summary = "책 삭제", description = "책 아이디를 통한 책 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(
        @Parameter(hidden = true) Authentication authentication,
        @PathVariable(name = "id") Long bookId) {

        bookService.deleteBook(authentication.getName(), bookId);
        return response.success(ResponseCode.BOOK_DELETED);
    }

    @Operation(summary = "책 구독", description = "책 아이디, 카테고리 아이디를 통한 책 구독")
    @PostMapping("/subscription")
    public ResponseEntity<?> subscribeBook(
        @Parameter(hidden = true) Authentication authentication,
        @RequestBody BookSubscribeDto bookSubscribeDto) {

        Long pickBookId = bookService.subscribeBook(authentication.getName(), bookSubscribeDto);
        return response.success(ResponseCode.BOOK_SUBSCRIBED, pickBookId);
    }

    @Operation(summary = "책 구독 삭제", description = "책 아이디를 통한 책 구독 취소")
    @DeleteMapping("/unsubscription/{id}")
    public ResponseEntity<?> unsubscribeBook(
        @Parameter(hidden = true) Authentication authentication,
        @PathVariable(name = "id") Long bookId) {

        bookService.unsubscribeBook(authentication.getName(), bookId);
        return response.success(ResponseCode.BOOK_UNSUBSCRIBED);
    }

    @Operation(summary = "책에 들어있는 페이퍼 삭제", description = "책에 포함된 페이퍼를 삭제, 책에서 제거할뿐 페이퍼 자체 삭제는 아님")
    @DeleteMapping("/{id}/delete-paper")
    public ResponseEntity<?> deletePaperFromBook(
        @Parameter(hidden = true) Authentication authentication,
        @PathVariable(name = "id") Long bookId, Long paperId) {

        bookService.deletePaperFromBook(authentication.getName(), bookId, paperId);
        return response.success(ResponseCode.PAPER_DELETE);
    }

    @Operation(summary = "페이퍼가 포함된 책 목록 조회", description = "해당 페이퍼가 들어있는 책 정보 목록을 반환")
    @GetMapping("/list/{id}")
    public ResponseEntity<?> getBookListFromPaper(
        @Parameter(hidden = true) Authentication authentication,
        @PathVariable(name = "id") Long paperId) {

        Member me = memberService.findMember(authentication.getName());
        Long myId = me.getId();
        return response.success(ResponseCode.BOOK_LIST_FROM_PAPER, bookService.getBookListFromPaper(myId, paperId));
    }


    /* 북마크는 일단 보류 */
//    @Operation(summary = "북마크 생성", description = "책에 대한 북마크 정보 생성 or 수정")
//    @PostMapping("/bookmark")
//    public ResponseEntity<?> createBookMarker(@RequestBody BookMarkerPostDto bookMarker) {
//
//        return response.success(ResponseCode.BOOKMARK_CREATED, bookMarker.getIndex());
//    }

}
