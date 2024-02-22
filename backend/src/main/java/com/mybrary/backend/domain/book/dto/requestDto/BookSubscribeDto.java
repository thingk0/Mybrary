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
public class BookSubscribeDto {

    /**
     *  책 구독 요청 request
     *
     */

    private Long bookId;
    private Long categoryId;

}
