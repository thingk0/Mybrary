package com.mybrary.backend.domain.contents.paper.dto.responseDto;

import com.mybrary.backend.domain.member.dto.responseDto.MemberGetDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomePaperGetDto {

    /**
     *  홈에서 스레드 조회할 때 페이퍼 List에 포함
     *
     */


    private Long paperId;
    private String createAt;
    private int layoutType;
    private String content1;
    private String content2;
    private String image1Url;
    private String image2Url;
    private String image3Url;
    private String image4Url;
    private String thumbnailImage1Url;
    private String thumbnailImage2Url;
    private String thumbnailImage3Url;
    private String thumbnailImage4Url;
    private List<String> tagList;
    private List<MemberGetDto> mentionList;
    private int likeCount;
    private int commentCount;
    private int scrapCount;
    private boolean isLiked;
    private boolean isPaperPublic;
    private boolean isScrapEnable;

}
