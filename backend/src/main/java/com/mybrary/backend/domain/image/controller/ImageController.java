package com.mybrary.backend.domain.image.controller;

import com.mybrary.backend.domain.image.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Image 컨트롤러", description = "Image Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/image")
public class ImageController {

    private final ImageService imageService;

    @Operation(summary = "이미지 업로드", description = "이미지 업로드")
    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam(required = false) MultipartFile file) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
