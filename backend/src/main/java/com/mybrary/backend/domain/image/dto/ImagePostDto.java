package com.mybrary.backend.domain.image.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImagePostDto {

    /**
     *  이미지 업로드 요청
     */

    private String name;
    private String originName;
    private String url;
    private String thumbnailUrl;
    private String format;
    private String size;

}
