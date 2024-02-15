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

    @Operation(summary = "추천 검색어 조회", description = "추천 검색어 조회")
    @GetMapping
    public ResponseEntity<?> getRealTimeSuggestedSearchTerms(@RequestParam(name = "keyword") String keyword) {

        return response.success(ResponseCode.REALTIME_RECOMMENDED_KEYWORDS_FETCHED,
                                searchService.getRealTimeSuggestedSearchTerms(keyword));
    }

    @Operation(summary = "최근 검색어 조회", description = "최근 검색어 조회")
    @GetMapping("/popular")
    public ResponseEntity<?> getTrendingSearchTerms() {

        return response.success(ResponseCode.POPULAR_KEYWORDS_FETCHED, searchService.getRecentSearchTerms());
    }

    @Operation(summary = "컨텐츠 검색", description = "컨텐츠 태그와 내용 기반 검색")
    @GetMapping("/contents")
    public ResponseEntity<?> searchByThread(@Parameter(hidden = true) Authentication authentication,
                                            @RequestParam(name = "keyword") String keyword,
                                            @PageableDefault(page = 0, size = 20) Pageable page) {

        return response.success(ResponseCode.CONTENTS_SEARCHED,
                                searchService.searchThread(authentication.getName(), keyword, page));
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

        List<MemberGetDto> accountList = searchService.searchAccount(authentication.getName(), keyword, page);
        HashMap<String, Object> map = new HashMap<>();
        map.put("accountList", accountList);
        map.put("page", page);

        return response.success(ResponseCode.ACCOUNTS_SEARCHED, map);
    }

    @Operation(summary = "멘션을 하기 위한 계정 검색", description = "계정 닉네임 or 이름 기반 검색")
    @GetMapping("/mention")
    public ResponseEntity<?> searchByAccountForMention(@RequestParam(name = "keyword") String keyword,
                                                       @PageableDefault(page = 0, size = 10) Pageable page) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);

        return response.success(ResponseCode.MENTION_ACCOUNTS_SEARCHED, map);
    }

}
