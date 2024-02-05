package com.mybrary.backend.domain.comment.controller;

import com.mybrary.backend.domain.comment.dto.CommentGetDto;
import com.mybrary.backend.domain.comment.dto.CommentPostDto;
import com.mybrary.backend.domain.member.dto.MemberInfoDto;
import com.mybrary.backend.global.format.ApiResponse;
import com.mybrary.backend.global.format.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Category 컨트롤러", description = "Category Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final ApiResponse response;

    @Operation(summary = "댓글 생성", description = "페이퍼에 대한 댓글 생성")
    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentPostDto comment) {

        return response.success(ResponseCode.COMMENT_CREATED.getMessage(), comment.getContent());
    }

    @Operation(summary = "댓글 삭제", description = "댓글 아이디를 통한 댓글 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {

        return response.success(ResponseCode.COMMENT_DELETED.getMessage(), commentId);
    }

    @Operation(summary = "페이퍼 댓글 조회", description = "페이퍼 아이디를 통한 페이퍼의 댓글 조회")
    @GetMapping
    public ResponseEntity<?> getAllComment(@RequestParam String paperId) {

        MemberInfoDto member1 = new MemberInfoDto(1L, "wnsgh", "안녕하세요 최준호입니다", "123123");
        MemberInfoDto member2 = new MemberInfoDto(2L, "aksrl", "안녕하세요 서만기입니다", "666666");
        MemberInfoDto member3 = new MemberInfoDto(3L, "gPtjs", "안녕하세요 박혜선입니다", "145643");
        MemberInfoDto member4 = new MemberInfoDto(4L, "thdud", "안녕하세요 최소영입니다", "000000");


        /* commentList를 위한 것 */
        List<MemberInfoDto> memberList1 = new ArrayList<>();
        memberList1.add(member1);
        memberList1.add(member2);

        /* recommentList를 위한 것 */
        List<MemberInfoDto> memberList2 = new ArrayList<>();
        memberList2.add(member3);

        CommentGetDto recomment1 = new CommentGetDto(11L, "2024-01-29", member2, false, "대댓글이다", 0,memberList2, null);
        List<CommentGetDto> recommentList = new ArrayList<>();
        recommentList.add(recomment1);

        CommentGetDto comment1 = new CommentGetDto(1L, "2024-01-28", member1, true, "하이", 1, memberList1, recommentList);
        CommentGetDto comment2 = new CommentGetDto(2L, "2024-01-20", member2, false, "이 게시글 재밌네", 4, memberList1, null);
        CommentGetDto comment3 = new CommentGetDto(3L, "2024-01-14", member3, false, "여기 어디에요", 2, memberList1, null);
        CommentGetDto comment4 = new CommentGetDto(4L, "2024-01-02", member4, false, "재밌겠다", 1, memberList1, null);

        List<CommentGetDto> list = new ArrayList<>();
        list.add(comment1);
        list.add(comment2);
        list.add(comment3);
        list.add(comment4);


        return response.success(ResponseCode.COMMENTS_FETCHED.getMessage(), list);
    }
}
