package com.mybrary.backend.domain.image.service;

import com.mybrary.backend.domain.image.entity.Image;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    Long uploadImage(MultipartFile file) throws IOException;
    Long createImage(Image image);

}
