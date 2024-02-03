package com.mybrary.backend.domain.mybrary.controller;

import com.mybrary.backend.domain.member.dto.MemberGetDto;
import com.mybrary.backend.domain.mybrary.dto.MybraryGetDto;
import com.mybrary.backend.domain.mybrary.dto.MybraryOtherGetDto;
import com.mybrary.backend.domain.mybrary.dto.MybraryUpdateDto;
import com.mybrary.backend.domain.mybrary.service.MybraryService;
import com.mybrary.backend.global.format.ApiResponse;
import com.mybrary.backend.global.format.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Mybrary 컨트롤러", description = "Mybrary Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mybrary")
public class MybraryController {

    private final ApiResponse response;
    private final MybraryService mybraryService;

    @Operation(summary = "나의 마이브러리 조회", description = "나의 마이브러리 정보 조회")
    @GetMapping
    public ResponseEntity<?> getMybrary() {

        MemberGetDto member = new MemberGetDto(1L, "hyeseon@ssafy.com", "박혜선", "hyeseon","안녕하세요", "profileUrl", true, true);
        MybraryGetDto mybrary = new MybraryGetDto(1L, "액자url", 4, 2, 1, 3, member, 20, 5, 10, 20, 1L, 1L);

        return response.success(ResponseCode.MYBRARY_FETCHED.getMessage(), mybrary);
    }

    @Operation(summary = "타인의 마이브러리 조회", description = "회원 아이디를 통한 회원의 마이브러리 정보 조회")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOtherMybrary(
        @PathVariable(name = "id") Long memberId) {

        MemberGetDto member = new MemberGetDto(1L, "hyeseon@ssafy.com", "박혜선", "hyeseon","안녕하세요", "profileUrl", true, true);
        MybraryOtherGetDto mybrary = new MybraryOtherGetDto(1L, "액자url", 4, 2, 1, 3, member, 20, 5, 10, 20, true, 1L, 1L);

        return response.success(ResponseCode.OTHER_MYBRARY_FETCHED.getMessage(), mybrary);

    }

    @Operation(summary = "마이브러리 수정", description = "마이브러리 정보 수정")
    @PutMapping
    public ResponseEntity<?> updateMybrary(@RequestBody MybraryUpdateDto mybrary) {

        return response.success(ResponseCode.MYBRARY_UPDATED.getMessage(), mybrary.getMybraryId());
    }


}
