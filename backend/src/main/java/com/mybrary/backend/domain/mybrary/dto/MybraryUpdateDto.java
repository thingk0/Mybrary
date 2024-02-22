package com.mybrary.backend.domain.mybrary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MybraryUpdateDto {

    /**
     *  마이브러리 수정 요청
     */

    private Long mybraryId;
    private Long frameImageId;
    private int backgroundColor;
    private int deskColor;
    private int bookshelfColor;
    private int easelColor;

}
