package com.mybrary.backend.domain.contents.paper.controller;

import com.mybrary.backend.domain.contents.paper.dto.PaperScrapDto;
import com.mybrary.backend.domain.contents.paper.dto.PaperShareDto;
import com.mybrary.backend.domain.contents.paper.service.PaperServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Paper 컨트롤러", description = "Paper Controller API")
@RequestMapping("/api/v1/paper")
@RestController
public class PaperControllerV1 {

    @Autowired
    private PaperServiceImpl paperService;

    @Operation(summary = "페이퍼 스크랩", description = "페이퍼 스크랩")
    @PostMapping("/scrap")
    public ResponseEntity<?> scrapPaper(@RequestBody PaperScrapDto scrap) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "페이퍼 공유", description = "페이퍼 다른 사용자에게 공유")
    @PostMapping("/share")
    public ResponseEntity<?> sharePaper(@RequestBody PaperShareDto share) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
