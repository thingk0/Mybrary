package com.mybrary.backend.domain.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Category 컨트롤러", description = "Category Controller API")
@RestController
@RequestMapping("/api/v1/comment")
public class CommentControllerV1 {

    @Operation(summary = "댓글 생성", description = "페이퍼에 대한 댓글 생성")
    @PostMapping
    public ResponseEntity<?> createComment() {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "댓글 삭제", description = "댓글 아이디를 통한 댓글 삭제")
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteComment(
        @RequestParam(name = "id") Long bookshelfId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "페이퍼 댓글 조회", description = "페이퍼 아이디를 통한 페이퍼의 댓글 조회")
    @GetMapping
    public ResponseEntity<?> getComments(
        @RequestParam(name = "id") String paperId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
