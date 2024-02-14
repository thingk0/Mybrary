package com.mybrary.backend.domain.rollingpaper.controller;

import com.mybrary.backend.domain.rollingpaper.dto.RollingPaperDto;
import com.mybrary.backend.domain.rollingpaper.service.RollingPaperService;
import com.mybrary.backend.global.format.code.ApiResponse;
import com.mybrary.backend.global.format.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@Tag(name = "RollingPaper 컨트롤러", description = "RollingPaper Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rollingpaper")
public class RollingPaperController {

    private final RollingPaperService rollingPaperService;
    private final ApiResponse response;

    @Operation(summary = "롤링페이퍼 조회", description = "롤링페이퍼 입장")
    @GetMapping("/{id}")
    public ResponseEntity<?> fetch(@Parameter(hidden = true) Authentication authentication,
                                   @PathVariable("id") Long rollingPaperId) {

        return response.success(ResponseCode.ROLLING_PAPER_FETCHED,
                                rollingPaperService.fetch(authentication.getName(), rollingPaperId));

    }

    @Operation(summary = "롤링페이퍼 저장", description = "롤링페이퍼 저장")
    @PutMapping
    public ResponseEntity<?> update(@Parameter(hidden = true) Authentication authentication,
                                    @RequestBody RollingPaperDto rollingPaper) {

        return response.success(ResponseCode.ROLLING_PAPER_SAVED,
                                rollingPaperService.update(authentication.getName(), rollingPaper));
    }

}
