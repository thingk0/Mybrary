package com.mybrary.backend.domain.bookmarker.dto.requestDto;

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
public class BookMarkerPostDto {

    /**
     *  책갈피 등록 or 수정
     *  (수정때도 그냥 원래 있던 책갈피 데이터 삭제하고 새로 등록하는거 어때)
     */


    private Long bookId;
    private int index;

}
