package com.mybrary.backend.domain.image.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageGetDto {

    /**
     *  이미지 정보 조회
     */

    private Long imageId;
    private String name;
    private String originName;
    private String url;
    private String thumbnailUrl;
    private String format;
    private String size;

}
