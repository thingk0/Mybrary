package com.mybrary.backend.domain.contents.paper.controller;

import com.mybrary.backend.domain.chat.dto.ChatMessagePostDto;
import com.mybrary.backend.domain.chat.service.ChatService;
import com.mybrary.backend.domain.contents.paper.dto.PaperScrapDto;
import com.mybrary.backend.domain.contents.paper.dto.PaperShareDto;
import com.mybrary.backend.domain.contents.paper.service.PaperService;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.member.service.MemberService;
import com.mybrary.backend.global.format.code.ApiResponse;
import com.mybrary.backend.global.format.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Paper 컨트롤러", description = "Paper Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/paper")
public class PaperController {

    private final ApiResponse response;
    private final PaperService paperService;
    private final MemberService memberService;
    private final ChatService chatService;

    @Operation(summary = "페이퍼 스크랩", description = "페이퍼 스크랩")
    @PostMapping("/scrap")
    public ResponseEntity<?> scrapPaper(@RequestBody PaperScrapDto scrap) {

        return response.success(ResponseCode.PAPER_SCRAPPED, scrap.getBookId());
    }

    @Operation(summary = "페이퍼 공유", description = "페이퍼 다른 사용자에게 공유")
    @PostMapping("/share")
    public ResponseEntity<?> sharePaper(@Parameter(hidden = true) Authentication authentication,
                                        @RequestBody ChatMessagePostDto thread) {

        Member member = memberService.findMember(authentication.getName());
        Long myId = member.getId();
        chatService.threadShare(myId, thread);
        return response.success(ResponseCode.PAPER_SHARED.getMessage());

    }

}
