package com.mybrary.backend.domain.category.dto.requestDto;

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
public class CategoryPostDto {

    /**
     *  새 카테고리를 추가할 때 요청
     *
     */

    private Long bookShelfId;
    private String name;

}
