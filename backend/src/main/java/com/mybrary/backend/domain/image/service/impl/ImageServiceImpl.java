package com.mybrary.backend.domain.image.service.impl;

import com.mybrary.backend.domain.image.entity.Image;
import com.mybrary.backend.domain.image.repository.ImageRepository;
import com.mybrary.backend.domain.image.service.ImageService;
import com.mybrary.backend.domain.image.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public String getImageUrl(Long imageId) {
        /* 노드 이미지 서버로부터 인증 url 받아서 반환 */
        /* 진행중..*/
        return null;
    }

//    @Override
//    public Long uploadImage() throws IOException {
//        return s3Uploader.uploadFile();
//    }

//    @Override
//    public Long uploadImage(MultipartFile file) throws IOException {
//        return s3Uploader.uploadFile(file);

//    }

}