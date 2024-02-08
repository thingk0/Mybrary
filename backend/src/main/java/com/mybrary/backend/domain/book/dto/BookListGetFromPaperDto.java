package com.mybrary.backend.domain.book.dto;

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
public class BookListGetFromPaperDto {

    private Long bookId;
    private String bookTitle;
    private Long memberId;
    private String nickname;
    private String profileImageUrl;

}
