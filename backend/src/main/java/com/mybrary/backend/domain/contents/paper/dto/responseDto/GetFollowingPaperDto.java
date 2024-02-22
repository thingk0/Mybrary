package com.mybrary.backend.domain.contents.paper.dto.responseDto;

import com.mybrary.backend.domain.book.dto.responseDto.BookForMainThreadDto;
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
public class GetFollowingPaperDto {

    private Long id;
    private List<BookForMainThreadDto> bookList;
    private int layoutType;

    private String content1;
    private String content2;
    private Long imageId1;
    private String imageUrl1;
    private Long imageId2;
    private String imageUrl2;

    private int likesCount;
    private int commentCount;
    private int scrapCount;
    private boolean isLiked;
    private List<String> tagList;
    private String mentionListString;
    private List<MentionListDto> mentionList;

    private boolean isPaperPublic;
    private boolean isScrapEnable;

    public GetFollowingPaperDto(Long paperId, int layoutType, String content1, String content2,
        int likesCount, int commentCount, int scrapCount, String mentionListString, boolean isPaperPublic, boolean isScrapEnable) {
        this.id = paperId;
        this.layoutType = layoutType;
        this.content1 = content1;
        this.content2 = content2;
        this.likesCount = likesCount;
        this.commentCount = commentCount;
        this.scrapCount = scrapCount;
        this.mentionListString = mentionListString;
        this.isPaperPublic = isPaperPublic;
        this.isScrapEnable = isScrapEnable;
    }

//    public GetFollowingPaperDto(Long id, int layoutType, String content1, String content2, boolean isPaperPublic,
//                                boolean isScrapEnable) {
//        this.id = id;
//        this.layoutType = layoutType;
//        this.content1 = content1;
//        this.content2 = content2;
//        this.isPaperPublic = isPaperPublic;
//        this.isScrapEnable = isScrapEnable;
//    }
}
