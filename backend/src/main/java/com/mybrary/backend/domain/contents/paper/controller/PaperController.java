package com.mybrary.backend.domain.contents.paper.controller;

import com.mybrary.backend.domain.contents.paper.dto.PaperScrapDto;
import com.mybrary.backend.domain.contents.paper.dto.PaperShareDto;
import com.mybrary.backend.domain.contents.paper.service.PaperService;
import com.mybrary.backend.global.format.ApiResponse;
import com.mybrary.backend.global.format.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @Operation(summary = "페이퍼 스크랩", description = "페이퍼 스크랩")
    @PostMapping("/scrap")
    public ResponseEntity<?> scrapPaper(@RequestBody PaperScrapDto scrap) {

        return response.success(ResponseCode.PAPER_SCRAPPED.getMessage(), scrap.getBookId());
    }

    @Operation(summary = "페이퍼 공유", description = "페이퍼 다른 사용자에게 공유")
    @PostMapping("/share")
    public ResponseEntity<?> sharePaper(@RequestBody PaperShareDto share) {

        return response.success(ResponseCode.PAPER_SHARED.getMessage(), share.getThreadId());
    }

}
