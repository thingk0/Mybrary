package com.mybrary.backend.domain.book.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookListGetFromPaperDto {

    private Long bookId;
    private String coverTitle;
    private String coverImageUrl;
    private Long memberId;

    private boolean isProfilePublic;
    private boolean isFollowed;

    public BookListGetFromPaperDto(Long bookId, String coverTitle, Long memberId, String coverImageUrl) {
        this.bookId = bookId;
        this.coverTitle = coverTitle;
        this.memberId = memberId;
        this.coverImageUrl = coverImageUrl;
    }

    public void updateIsProfilePublic(boolean bool) {
        this.isProfilePublic = bool;
    }

    public void updateIsFollowed(boolean bool) {
        this.isFollowed = bool;
    }

}
