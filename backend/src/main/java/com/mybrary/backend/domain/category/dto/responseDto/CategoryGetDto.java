package com.mybrary.backend.domain.category.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryGetDto {

    /**
     *  책장을 눌렀을 때 보이는 카테고리 리스트 조회
     *
     */

    private Long categoryId;
    private String name;
    private int seq;
    private int bookCount;

}
