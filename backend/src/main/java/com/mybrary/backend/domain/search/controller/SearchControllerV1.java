package com.mybrary.backend.domain.search.controller;

import com.mybrary.backend.domain.book.dto.BookGetDto;
import com.mybrary.backend.domain.contents.thread.dto.HomeThreadGetDto;
import com.mybrary.backend.domain.contents.thread.dto.ThreadSimpleGetDto;
import com.mybrary.backend.domain.member.dto.MemberGetDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Search 컨트롤러", description = "Search Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchControllerV1 {

    @Operation(summary = "인기검색어 조회", description = "인기검색어 조회")
    @GetMapping("/popular")
    public ResponseEntity<?> getPopularKeyword() {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "스레드 검색", description = "스레드 태그 기반 검색")
    @GetMapping("/thread")
    public ResponseEntity<?> searchByThread(@RequestParam(name = "keyword") String keyword,
                                            @PageableDefault(page = 0, size = 10) Pageable page) {

        return new ResponseEntity<List<ThreadSimpleGetDto>>(HttpStatus.OK);
    }

    @Operation(summary = "책 검색", description = "책 제목 기반 검색")
    @GetMapping("/book")
    public ResponseEntity<?> searchByBook(@RequestParam(name = "keyword") String keyword,
                                          @PageableDefault(page = 0, size = 10) Pageable page) {

        return new ResponseEntity<List<BookGetDto>>(HttpStatus.OK);
    }

    @Operation(summary = "계정 검색", description = "계정 닉네임 or 이름 기반 검색")
    @GetMapping("/account")
    public ResponseEntity<?> searchByAccount(@RequestParam(name = "keyword") String keyword,
                                             @PageableDefault(page = 0, size = 10) Pageable page) {

        return new ResponseEntity<List<MemberGetDto>>(HttpStatus.OK);
    }

    @Operation(summary = "멘션을 하기 위한 계정 검색", description = "계정 닉네임 or 이름 기반 검색")
    @GetMapping("/mention")
    public ResponseEntity<?> searchByAccountForMention(
        @RequestParam(name = "keyword") String keyword,
        @PageableDefault(page = 0, size = 10) Pageable page) {

        return new ResponseEntity<List<MemberGetDto>>(HttpStatus.OK);
    }


}
