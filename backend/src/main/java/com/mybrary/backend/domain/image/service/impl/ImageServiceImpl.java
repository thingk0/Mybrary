package com.mybrary.backend.domain.image.service.impl;

import com.mybrary.backend.domain.image.entity.Image;
import com.mybrary.backend.domain.image.repository.ImageRepository;
import com.mybrary.backend.domain.image.service.ImageService;
import com.mybrary.backend.domain.image.service.S3Uploader;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final S3Uploader s3Uploader;

    @Override
    public Long createImage(Image image) {
        return imageRepository.save(image).getId();
    }

    @Override
    public Long uploadImage(MultipartFile file) throws IOException {
        return s3Uploader.uploadFile(file);
    }

}