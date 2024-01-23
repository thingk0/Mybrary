package com.mybrary.backend.domain.contents.paper.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Paper 컨트롤러", description = "Paper Controller API")
@RequestMapping("/api/v1/paper")
@RestController
public class PaperControllerV1{

    @Operation(summary = "페이퍼 스크랩", description = "페이퍼 스크랩")
    @GetMapping("/{paperid}/scrap")
    public ResponseEntity<?> scrapPaper(
        @PathVariable(name = "paperid") String paperId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "페이퍼 공유", description = "페이퍼 다른 사용자에게 공유")
    @PostMapping("/share")
    public ResponseEntity<?> sharePaper(@RequestBody String test) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "페이퍼 댓글 조회", description = "페이퍼 아이디를 통한 페이퍼의 댓글 조회")
    @GetMapping("/{paperid}/comments")
    public ResponseEntity<?> getPaperComments(
        @PathVariable(name = "paperid") String paperId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
