package com.mybrary.backend.domain.image.controller;

import com.mybrary.backend.domain.image.entity.Image;
import com.mybrary.backend.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Image 컨트롤러", description = "Image Controller API")
@RestController
@RequestMapping("/api/v1/image")
public class ImageControllerV1 {

    @Operation(summary = "이미지 업로드", description = "이미지 업로드")
    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestBody Image image) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
