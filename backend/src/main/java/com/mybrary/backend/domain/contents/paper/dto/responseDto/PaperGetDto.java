package com.mybrary.backend.domain.contents.paper.dto.responseDto;

import java.time.LocalDateTime;
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
public class PaperGetDto {

    /**
     * 내 스레드 조회에서 페이퍼 List에 포함
     */
    private Long paperId;
    private LocalDateTime createdAt;
    private int layoutType;
    private String content1;
    private String content2;

    private Long imageId1;
    private String imageUrl1;
    private Long imageId2;
    private String imageUrl2;

    private int likeCount;
    private int commentCount;
    private int scrapCount;
    private boolean isLiked;
    private List<String> tagList;
    private String mentionListString;
    private List<MentionListDto> mentionList;

    /* isLiked, tagList는 따로 바인딩 */
    public PaperGetDto(Long paperId, LocalDateTime createdAt, int layoutType, String content1, String content2, String mentionListString) {

        this.paperId = paperId;
        this.createdAt = createdAt;
        this.layoutType = layoutType;
        this.content1 = content1;
        this.content2 = content2;
        this.mentionListString = mentionListString;
    }

    public void updateIsLiked(boolean bool){
        isLiked = bool;
    }
    public void updateTagList(List<String> tagList){
        this.tagList = tagList;
    }

    public void updateImageId1(Long imageId1){
        this.imageId1 = imageId1;
    }
    public void updateImageId2(Long imageId2){
        this.imageId2 = imageId2;
    }
    public void updateImageUrl1(String imageUrl1){
        this.imageUrl1 = imageUrl1;
    }
    public void updateImageUrl2(String imageUrl2){
        this.imageUrl2 = imageUrl2;
    }

}
