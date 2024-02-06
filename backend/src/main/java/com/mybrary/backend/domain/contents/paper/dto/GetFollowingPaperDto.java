package com.mybrary.backend.domain.contents.paper.dto;

import com.querydsl.core.annotations.QueryProjection;
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

    private int layoutType;

    private String content1;

    private String content2;

    private int likesCount;

    private int commentCount;

    private int scrapCount;

    private boolean isScrapEnabled;

    private String imageUrl1;
    private String imageUrl2;
    private boolean isLiked;
    private List<String> tagList;

    @QueryProjection
    public GetFollowingPaperDto(Long paperId, int layoutType, String content1, String content2,
        int likesCount, int commentCount, int scrapCount, boolean isScrapEnabled) {
        this.id = paperId;
        this.layoutType = layoutType;
        this.content1 = content1;
        this.content2 = content2;
        this.likesCount = likesCount;
        this.commentCount = commentCount;
        this.scrapCount = scrapCount;
        this.isScrapEnabled = isScrapEnabled;
    }

}
