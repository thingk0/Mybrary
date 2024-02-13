package com.mybrary.backend.domain.contents.paper.dto.responseDto;

import com.mybrary.backend.domain.member.dto.responseDto.MemberInfoDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaperInBookGetDto {

    /**
     *  책에 포함될 페이퍼에 대한 List
     *  다른점 : 이건 페이퍼를 작성한 사람 정보까지 있음
     */

    private Long paperId;
    private Long threadId;
    private LocalDateTime createdAt;
    private MemberInfoDto writer;
    private int layoutType;
    private String content1;
    private String content2;
    private Long imageId1;
    private String imageUrl1;
    private Long imageId2;
    private String imageUrl2;
    private List<String> tagList;
    private int likeCount;
    private int commentCount;
    private int scrapCount;
    private boolean isLiked;

    public PaperInBookGetDto(Long paperId, LocalDateTime createdAt, int layoutType, String content1, String content2) {
        this.paperId = paperId;
        this.createdAt = createdAt;
        this.layoutType = layoutType;
        this.content1 = content1;
        this.content2 = content2;
    }
}
