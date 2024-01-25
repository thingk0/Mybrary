package com.mybrary.backend.domain.category.dto;

import java.util.List;
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
public class CategoryUpdateDto {

    /**
     *  카테고리 수정 요청
     *  책장 아이디를 받고 카테고리가 순서대로 정리된 리스트를 받기
     */

    private Long bookShelfId;
    private List<CategoryGetDto> categoryList;

}
