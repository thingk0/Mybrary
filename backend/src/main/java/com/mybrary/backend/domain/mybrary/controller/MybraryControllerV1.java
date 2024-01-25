package com.mybrary.backend.domain.mybrary.controller;

import com.mybrary.backend.domain.mybrary.dto.MybraryGetDto;
import com.mybrary.backend.domain.mybrary.dto.MybraryOtherGetDto;
import com.mybrary.backend.domain.mybrary.dto.MybraryUpdateDto;
import com.mybrary.backend.domain.mybrary.service.MybraryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Mybrary 컨트롤러", description = "Mybrary Controller API")
@RestController
@RequestMapping("/api/v1/mybrary")
public class MybraryControllerV1 {

    @Autowired
    private MybraryServiceImpl mybraryService;

    @Operation(summary = "나의 마이브러리 조회", description = "나의 마이브러리 정보 조회")
    @GetMapping
    public ResponseEntity<?> getMybrary() {

        return new ResponseEntity<MybraryGetDto>(HttpStatus.OK);
    }

    @Operation(summary = "타인의 마이브러리 조회", description = "회원 아이디를 통한 회원의 마이브러리 정보 조회")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOtherMybrary(
        @PathVariable(name = "id") Long memberId) {

        return new ResponseEntity<MybraryOtherGetDto>(HttpStatus.OK);
    }

    @Operation(summary = "마이브러리 수정", description = "마이브러리 정보 수정")
    @PutMapping
    public ResponseEntity<?> updateMybrary(@RequestBody MybraryUpdateDto mybrary) {

        return new ResponseEntity<Long>(HttpStatus.OK);
    }


}
