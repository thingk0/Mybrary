package com.mybrary.backend.domain.book.dto.requestDto;

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
public class BookPostDto {

    /**
     *  책 생성
     *
     */

    private String title;
    private Long coverImageId;
    private int coverLayout;
    private int coverColorCode;
    private Long categoryId;

}
