package com.mybrary.backend.domain.search.controller;

import com.mybrary.backend.domain.book.dto.responseDto.BookGetDto;
import com.mybrary.backend.domain.member.dto.responseDto.MemberGetDto;
import com.mybrary.backend.domain.search.service.SearchService;
import com.mybrary.backend.global.format.code.ApiResponse;
import com.mybrary.backend.global.format.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Operation(summary = "컨텐츠 검색", description = "컨텐츠 태그와 내용 기반 검색")
    @GetMapping("/contents")
    public ResponseEntity<?> searchByThread(@RequestParam(name = "keyword") String keyword,
                                            @PageableDefault(page = 0, size = 20) Pageable page) {

        return response.success(ResponseCode.CONTENTS_SEARCHED, searchService.searchThread(keyword, page));
    }

    @Operation(summary = "책 검색", description = "책 제목 기반 검색")
    @GetMapping("/book")
    public ResponseEntity<?> searchByBook(@Parameter(hidden = true) Authentication authentication,
                                          @RequestParam(name = "keyword") String keyword,
                                          @PageableDefault(page = 0, size = 10) Pageable page) {

        List<BookGetDto> result = searchService.searchBook(authentication.getName(), keyword, page);
        HashMap<String, Object> map = new HashMap<>();
        map.put("bookList", result);
        map.put("page", page);

        return response.success(ResponseCode.BOOKS_SEARCHED, map);
    }

    @Operation(summary = "계정 검색", description = "계정 닉네임 or 이름 기반 검색")
    @GetMapping("/account")
    public ResponseEntity<?> searchByAccount(@Parameter(hidden = true) Authentication authentication,
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
