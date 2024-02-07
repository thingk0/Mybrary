package com.mybrary.backend.domain.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.mybrary.backend.domain.image.entity.Image;
import com.mybrary.backend.domain.image.repository.ImageRepository;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Component
@PropertySource("classpath:application-aws.yml")
public class S3Uploader {

    private final AmazonS3 amazonS3;
    private final ImageRepository imageRepository;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    private String dir = "/profile";    //    폴더명, 어떤 이미지 파일인지 구분 (프로필, 스레드, 액자)

    // 렌덤한 파일명 생성 (중복 예방하기 위함)
    private String generateFileName(MultipartFile file) {
        return UUID.randomUUID().toString() + "-" + file.getName();
    }

    public Long uploadFile() throws IOException {
//        String fileName = generateFileName(file);
//        String bucketDir = bucketName + dir;
//        amazonS3.putObject(bucketDir, fileName, file.getInputStream(), getObjectMetadata(file));
//        String format = getObjectMetadata(file).getContentType();
//        double size = getObjectMetadata(file).getContentLength();
        Image image = Image.builder()
                           .name("test")
                           .url("url")
                           .format("format")
//                           .size()
                           .build();
        imageRepository.save(image);
        return image.getId();

    }

    private ObjectMetadata getObjectMetadata(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());
        return objectMetadata;
    }

}

