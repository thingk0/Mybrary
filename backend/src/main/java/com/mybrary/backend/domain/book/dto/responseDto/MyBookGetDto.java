package com.mybrary.backend.domain.book.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyBookGetDto {

    /**
     *  스레드 작성할 때 or 스크랩할 때 책 선택 or 책갈피가 있는 책 목록 조회에서 사용
     *  나의 책 목록 DTO
     *  MyCategoryGetDto 안에 포함되서 사용
     */

    private Long bookId;
    private String title;
    private Long paperCount;

}
