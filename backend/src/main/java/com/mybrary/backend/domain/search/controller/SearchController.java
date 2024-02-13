package com.mybrary.backend.domain.search.controller;

import com.mybrary.backend.domain.book.dto.responseDto.BookGetDto;
import com.mybrary.backend.domain.contents.thread.dto.responseDto.ThreadSearchGetDto;
import com.mybrary.backend.domain.member.dto.responseDto.MemberGetDto;
import com.mybrary.backend.domain.member.dto.responseDto.MemberInfoDto;
import com.mybrary.backend.domain.search.service.SearchService;
import com.mybrary.backend.domain.search.service.impl.SearchServiceImpl;
import com.mybrary.backend.global.format.code.ApiResponse;
import com.mybrary.backend.global.format.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Search 컨트롤러", description = "Search Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchController {

    private final ApiResponse response;
    private final SearchService searchService;

    @Operation(summary = "인기검색어 조회", description = "인기검색어 조회")
    @GetMapping("/popular")
    public ResponseEntity<?> getPopularKeyword() {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "스레드 검색", description = "스레드 태그 기반 검색")
    @GetMapping("/thread")
    public ResponseEntity<?> searchByThread(@Parameter(hidden = true) Authentication authentication, @RequestParam(name = "keyword") String keyword,
                                            @PageableDefault(page = 0, size = 10) Pageable page) {


//        ThreadSimpleGetDto thread1 = new ThreadSimpleGetDto(1L, writer1, "썸네일url", 3132143, 12343, 13134);
//        ThreadSimpleGetDto thread2 = new ThreadSimpleGetDto(2L, writer2, "썸네일url", 3443, 96354, 999);
//        ThreadSimpleGetDto thread3 = new ThreadSimpleGetDto(3L, writer4, "썸네일url", 55225, 34534, 433);
//        ThreadSimpleGetDto thread4 = new ThreadSimpleGetDto(4L, writer1, "썸네일url", 342434, 44554, 22);
//        ThreadSimpleGetDto thread5 = new ThreadSimpleGetDto(5L, writer3, "썸네일url", 858, 222, 232);
//        ThreadSimpleGetDto thread6 = new ThreadSimpleGetDto(6L, writer1, "썸네일url", 321323, 4534, 23425);
//        ThreadSimpleGetDto thread7 = new ThreadSimpleGetDto(7L, writer2, "썸네일url", 23443, 12343, 42);


        List<ThreadSearchGetDto> list = new ArrayList<>();
//        list.add(thread1);
//        list.add(thread2);
//        list.add(thread3);
//        list.add(thread4);
//        list.add(thread5);
//        list.add(thread6);
//        list.add(thread7);
        List<ThreadSearchGetDto> result = searchService.searchThread(authentication.getName(), keyword, page);
        HashMap<String, Object> map = new HashMap<>();
        map.put("threadList", list);
        map.put("page", page);

        return response.success(ResponseCode.THREAD_SEARCHED, map);
    }

    @Operation(summary = "책 검색", description = "책 제목 기반 검색")
    @GetMapping("/book")
    public ResponseEntity<?> searchByBook(@Parameter(hidden = true) Authentication authentication, @RequestParam(name = "keyword") String keyword,
                                          @PageableDefault(page = 0, size = 10) Pageable page) {

//        MemberInfoDto writer1 = new MemberInfoDto(1L, "wnsgh", "안녕하세요 최준호입니다", "123123");
//        MemberInfoDto writer2 = new MemberInfoDto(2L, "aksrl", "안녕하세요 서만기입니다", "666666");
//        MemberInfoDto writer3 = new MemberInfoDto(3L, "gPtjs", "안녕하세요 박혜선입니다", "145643");
//        MemberInfoDto writer4 = new MemberInfoDto(4L, "thdud", "안녕하세요 최소영입니다", "000000");
//
//        BookGetDto book1 = new BookGetDto(1L, writer1, "준호의 그림일기", "1234", 2, 4, 0);
//        BookGetDto book2 = new BookGetDto(2L, writer2, "만기의 지각", "1234", 1, 1, 2);
//        BookGetDto book3 = new BookGetDto(3L, writer3, "혜선이의 여행", "1234", 4, 2, 1);
//        BookGetDto book4 = new BookGetDto(4L, writer4, "소영이의 일상", "1234", 3, 1, 0);

//
//        List<BookGetDto> list = new ArrayList<>();
//        list.add(book1);
//        list.add(book2);
//        list.add(book3);
//        list.add(book4);

        List<BookGetDto> result = searchService.searchBook(authentication.getName(), keyword, page);
        HashMap<String, Object> map = new HashMap<>();
        map.put("bookList", result);
        map.put("page", page);


        return response.success(ResponseCode.BOOKS_SEARCHED, map);
    }

    @Operation(summary = "계정 검색", description = "계정 닉네임 or 이름 기반 검색")
    @GetMapping("/account")
    public ResponseEntity<?> searchByAccount(@Parameter(hidden = true) Authentication authentication, @RequestParam(name = "keyword") String keyword,
                                             @PageableDefault(page = 0, size = 10) Pageable page) {

//        MemberGetDto memberdetail1 = new MemberGetDto(1L, "wndgh@ssafy.com", "최준호", "wnsgh", "안녕하세요 최준호입니다", "123123", true,
//                                                      true);
//        MemberGetDto memberdetail2 = new MemberGetDto(2L, "aksrl@ssafy.com", "서만기", "aksrl", "안녕하세요 서만기입니다", "666666", true,
//                                                      true);
//        MemberGetDto memberdetail3 = new MemberGetDto(3L, "gPtjs@ssafy.com", "박헤선", "gPtjs", "안녕하세요 박혜선입니다", "145643", true,
//                                                      true);
//        MemberGetDto memberdetail4 = new MemberGetDto(4L, "thdud@ssafy.com", "최소영", "thdud", "안녕하세요 최소영입니다", "000000", true,
//                                                      true);
//
//        List<MemberGetDto> list = new ArrayList<>();
//        list.add(memberdetail1);
//        list.add(memberdetail2);
//        list.add(memberdetail3);
//        list.add(memberdetail4);

        List<MemberGetDto> accountList = searchService.searchAccount(authentication.getName(), keyword, page);
        HashMap<String, Object> map = new HashMap<>();
        map.put("accountList", accountList);
        map.put("page", page);

        return response.success(ResponseCode.ACCOUNTS_SEARCHED, map);
    }

    @Operation(summary = "멘션을 하기 위한 계정 검색", description = "계정 닉네임 or 이름 기반 검색")
    @GetMapping("/mention")
    public ResponseEntity<?> searchByAccountForMention(
        @RequestParam(name = "keyword") String keyword,
        @PageableDefault(page = 0, size = 10) Pageable page) {

//        MemberGetDto memberdetail1 = new MemberGetDto(1L, "wndgh@ssafy.com", "최준호", "wnsgh", "안녕하세요 최준호입니다", "123123", true,
//                                                      true);
//        MemberGetDto memberdetail2 = new MemberGetDto(2L, "aksrl@ssafy.com", "서만기", "aksrl", "안녕하세요 서만기입니다", "666666", true,
//                                                      true);
//        MemberGetDto memberdetail3 = new MemberGetDto(3L, "gPtjs@ssafy.com", "박헤선", "gPtjs", "안녕하세요 박혜선입니다", "145643", true,
//                                                      true);
//        MemberGetDto memberdetail4 = new MemberGetDto(4L, "thdud@ssafy.com", "최소영", "thdud", "안녕하세요 최소영입니다", "000000", true,
//                                                      true);

//        List<MemberGetDto> list = new ArrayList<>();
//        list.add(memberdetail1);
//        list.add(memberdetail2);
//        list.add(memberdetail3);
//        list.add(memberdetail4);

        HashMap<String, Object> map = new HashMap<>();
//        map.put("accountList", list);
        map.put("page", page);

        return response.success(ResponseCode.MENTION_ACCOUNTS_SEARCHED, map);
    }

}
