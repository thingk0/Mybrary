package com.mybrary.backend.domain.category.dto;

import com.mybrary.backend.domain.book.dto.MyBookGetDto;
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
public class MyCategoryGetDto {

    /**
     *  스레드 작성할 때 or 스크랩할 때 책 선택 or 책갈피가 있는 책 목록 조회에서 사용
     *  나의 책 목록 DTO
     *  MyBookGetDto 리스트 포함해서 사용
     */

    private Long categoryId;
    private String categoryName;
    private List<MyBookGetDto> bookList;

}
