package com.mybrary.backend.domain.rollingpaper.controller;

import com.mybrary.backend.domain.rollingpaper.dto.RollingPaperGetDto;
import com.mybrary.backend.domain.rollingpaper.dto.RollingPaperPostDto;
import com.mybrary.backend.domain.rollingpaper.service.RollingPaperService;
import com.mybrary.backend.global.format.code.ApiResponse;
import com.mybrary.backend.global.format.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "RollingPaper 컨트롤러", description = "RollingPaper Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rollingpaper")
public class RollingPaperController {

    private final ApiResponse response;
    private final RollingPaperService rollingPaperService;

    @Operation(summary = "롤링페이퍼 조회", description = "롤링페이퍼 입장")
    @GetMapping("/{id}")
    public ResponseEntity<?> getRollingPaper(@Parameter(hidden = true) Authentication authentication,
                                             @PathVariable("id") Long rollingPaperId) {

        RollingPaperGetDto rollingPaper = rollingPaperService.getRollingPaper(authentication.getName(), rollingPaperId);
        return response.success(ResponseCode.ROLLING_PAPER_FETCHED, rollingPaper);
    }

    @Operation(summary = "롤링페이퍼 저장", description = "롤링페이퍼 저장")
    @PostMapping
    public ResponseEntity<?> saveRollingPaper(@Parameter(hidden = true) Authentication authentication,
                                              @RequestBody RollingPaperPostDto rollingPaper) {

        Long rollingPaperId = rollingPaperService.saveRollingPaper(authentication.getName(), rollingPaper);
        return response.success(ResponseCode.ROLLING_PAPER_SAVED, rollingPaperId);
    }

}
