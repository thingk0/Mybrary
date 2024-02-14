package com.mybrary.backend.domain.book.dto.responseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
//@AllArgsConstructor
@Builder
public class BookListGetFromPaperDto {


//    private Long memberId;
//    private String nickname;
//    private Long profileImageId;
//    private String profileImageUrl;
//    private String bookTitle;
//    private Long imageId;
//    private String imageUrl;
//    private int coverLayout;
//    private int coverColorCode;

    private Long bookId;
    private String coverTitle;
    private Long memberId;
    private String coverImageUrl;

    public BookListGetFromPaperDto(Long bookId, String coverTitle, Long memberId, String coverImageUrl) {
        this.bookId = bookId;
        this.coverTitle = coverTitle;
        this.memberId = memberId;
        this.coverImageUrl = coverImageUrl;
    }

//    private String nickname;

//    private Long profileImageId;
//    private String profileImageUrl;


//    private int coverLayout;
//    private int coverColorCode;
//    private String coverTitle;


//    private Long coverImageId;

}
