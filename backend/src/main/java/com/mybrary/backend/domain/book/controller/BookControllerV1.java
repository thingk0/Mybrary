package com.mybrary.backend.domain.book.controller;

import com.mybrary.backend.domain.book.dto.BookPostDto;
import com.mybrary.backend.domain.book.dto.BookSubscribeDto;
import com.mybrary.backend.domain.book.dto.BookUpdateDto;
<<<<<<< Updated upstream
import com.mybrary.backend.domain.book.service.BookService;
=======
import com.mybrary.backend.domain.book.dto.MyBookGetDto;
>>>>>>> Stashed changes
import com.mybrary.backend.domain.bookmarker.dto.BookMarkerPostDto;
import com.mybrary.backend.domain.category.dto.MyCategoryGetDto;
import com.mybrary.backend.domain.contents.paper.dto.PaperInBookGetDto;
import com.mybrary.backend.domain.member.dto.MemberInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
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

        MyBookGetDto myBook1 = new MyBookGetDto(1L, "부산여행", 17);
        MyBookGetDto myBook2 = new MyBookGetDto(2L, "일본여행", 2);
        MyBookGetDto myBook3 = new MyBookGetDto(3L, "대전여행", 8);

        List<MyBookGetDto> bookList1 = new ArrayList<>();
        bookList1.add(myBook1);
        bookList1.add(myBook2);
        bookList1.add(myBook3);

        MyCategoryGetDto myCategory1 = new MyCategoryGetDto(1L, "여행", bookList1);

        MyBookGetDto myBook4 = new MyBookGetDto(4L, "백엔드", 19);
        MyBookGetDto myBook5 = new MyBookGetDto(5L, "프론트엔드", 7);

        List<MyBookGetDto> bookList2 = new ArrayList<>();
        bookList2.add(myBook4);
        bookList2.add(myBook5);

        MyCategoryGetDto myCategory2 = new MyCategoryGetDto(2L, "공부", bookList2);

        List<MyCategoryGetDto> list = new ArrayList<>();
        list.add(myCategory1);
        list.add(myCategory2);

        return new ResponseEntity<List<MyCategoryGetDto>>(list, HttpStatus.OK);
    }

    @Operation(summary = "책 생성", description = "책 생성")
    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody BookPostDto book) {

        return new ResponseEntity<String>(book.getTitle(), HttpStatus.OK);
    }

    @Operation(summary = "북마크 생성", description = "책에 대한 북마크 정보 생성 or 수정")
    @PostMapping("/bookmark")
    public ResponseEntity<?> createBookMarker(@RequestBody BookMarkerPostDto bookMarker) {

        return new ResponseEntity<Integer>(bookMarker.getIndex(), HttpStatus.OK);
    }

    @Operation(summary = "책 정보 조회", description = "책 아이디를 통한 책 정보 조회")
    @GetMapping("/{id}")
    public ResponseEntity<?> getBook(@PathVariable(name = "id") Long bookId) {

        MemberInfoDto writer = new MemberInfoDto(1L, "hyeseon", "백엔드개발자", "123");

        List<String> tagList = new ArrayList<>();
        tagList.add("태그1");
        tagList.add("tag2");

        List<MemberInfoDto> mentionList = new ArrayList<>();
        mentionList.add(new MemberInfoDto(1L, "닉넴", "자기소개", "Url"));
        mentionList.add(new MemberInfoDto(2L, "닉넴2", "자기소개2", "Url2"));

        PaperInBookGetDto paper1 = PaperInBookGetDto.builder()
                                                   .paperId(1L)
                                                   .createdAt("2023-12-12")
                                                   .writer(writer)
                                                   .layoutType(1)
                                                   .content1("내용입니다")
                                                   .content1("어쩌구저저구")
                                                   .image1Url("1")
                                                   .image2Url("2")
                                                   .image3Url("3")
                                                   .image4Url("4")
                                                   .thumbnailImage1Url("1")
                                                   .thumbnailImage2Url("2")
                                                   .thumbnailImage3Url("3")
                                                   .thumbnailImage4Url("4")
                                                   .tagList(tagList)
                                                   .mentionList(mentionList)
                                                   .likeCount(2589423)
                                                   .commentCount(13)
                                                   .scrapCount(422)
                                                   .isLiked(true)
                                                   .build();

        PaperInBookGetDto paper2 = PaperInBookGetDto.builder()
                                                    .paperId(1L)
                                                    .createdAt("2023-12-12")
                                                    .writer(writer)
                                                    .layoutType(1)
                                                    .content1("내용입니다")
                                                    .content1("어쩌구저저구")
                                                    .image1Url("1")
                                                    .image2Url("2")
                                                    .image3Url("3")
                                                    .image4Url("4")
                                                    .thumbnailImage1Url("1")
                                                    .thumbnailImage2Url("2")
                                                    .thumbnailImage3Url("3")
                                                    .thumbnailImage4Url("4")
                                                    .tagList(tagList)
                                                    .mentionList(mentionList)
                                                    .likeCount(2589423)
                                                    .commentCount(13)
                                                    .scrapCount(422)
                                                    .isLiked(true)
                                                    .build();

        List<PaperInBookGetDto> list = new ArrayList<>();
        list.add(paper1);
        list.add(paper2);

        return new ResponseEntity<List<PaperInBookGetDto>>(list, HttpStatus.OK);
    }

    @Operation(summary = "책 수정", description = "책 아이디를 통한 책 정보 수정")
    @PutMapping
    public ResponseEntity<?> updateBook(@RequestBody BookUpdateDto book) {

        return new ResponseEntity<String>(book.getTitle(), HttpStatus.OK);
    }

    @Operation(summary = "책 삭제", description = "책 아이디를 통한 책 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable(name = "id") Long bookId) {

        return new ResponseEntity<Long>(bookId, HttpStatus.OK);
    }

    @Operation(summary = "책 구독", description = "책 아이디, 카테고리 아이디를 통한 책 구독")
    @PostMapping("/subscription")
    public ResponseEntity<?> subscribeBook(@RequestBody BookSubscribeDto bookSubscribe) {

        return new ResponseEntity<Long>(bookSubscribe.getBookId(), HttpStatus.OK);
    }

    @Operation(summary = "책 구독 삭제", description = "책 아이디를 통한 책 구독 취소")
    @DeleteMapping("/unsubscription/{id}")
    public ResponseEntity<?> unsubscribeBook(@PathVariable(name = "id") Long bookId) {

        return new ResponseEntity<>(bookId, HttpStatus.OK);
    }

}
