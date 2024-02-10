package com.mybrary.backend.domain.image.service;

import com.mybrary.backend.domain.image.entity.Image;

public interface ImageService {


    Long createImage(Image image);
    String getImageUrl(Long imageId);

    //    Long uploadImage(MultipartFile file) throws IOException;
    //    Long uploadImage() throws IOException;
}
