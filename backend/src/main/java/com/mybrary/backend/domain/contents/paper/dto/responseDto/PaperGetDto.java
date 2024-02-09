package com.mybrary.backend.domain.contents.paper.dto.responseDto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaperGetDto {

    /**
     * 내 스레드 조회에서 페이퍼 List에 포함
     */

    private Long paperId;
    private LocalDateTime createdAt;
    private int layoutType;
    private String content1;
    private String content2;
    private String image1Url;
    private String image2Url;
    private int likeCount;
    private int commentCount;
    private int scrapCount;
    private boolean isLiked;
    private List<String> tagList;

    /* isLiked, tagList는 따로 바인딩 */
    public PaperGetDto(Long paperId, LocalDateTime createdAt, int layoutType, String content1, String content2, String image1Url,
                       String image2Url, int likeCount, int commentCount, int scrapCount) {
        this.paperId = paperId;
        this.createdAt = createdAt;
        this.layoutType = layoutType;
        this.content1 = content1;
        this.content2 = content2;
        this.image1Url = image1Url;
        this.image2Url = image2Url;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.scrapCount = scrapCount;
    }

    public void updateIsLiked(boolean bool){
        isLiked = bool;
    }
    public void updateTagList(List<String> tagList){
        this.tagList = tagList;
    }

}
