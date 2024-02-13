package com.mybrary.backend.domain.book.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookListGetFromPaperDto {

    private Long bookId;
    private Long memberId;
    private String nickname;
    private Long profileImageId;
    private String profileImageUrl;
    private String bookTitle;
    private Long imageId;
    private String imageUrl;
    private int coverLayout;
    private int coverColorCode;

}
