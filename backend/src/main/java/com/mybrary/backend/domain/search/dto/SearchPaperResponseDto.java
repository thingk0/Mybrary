package com.mybrary.backend.domain.search.dto;

import com.mybrary.backend.domain.contents.paper.entity.Paper;
import com.mybrary.backend.domain.image.entity.Image;
import com.mybrary.backend.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchPaperResponseDto {

    private Long threadId;
    private Long paperId;
    private String profileImageUrl;
    private String nickname;
    private String name;
    private int likeCnt;
    private int commentCnt;
    private int scrapCnt;
    private String imageUrl;

    public static SearchPaperResponseDto of(Paper paper, Member member, Image image) {
        return SearchPaperResponseDto.builder()
                                     .threadId(paper.getThread().getId())
                                     .paperId(paper.getId())
                                     .profileImageUrl(member.getProfileImage().getUrl())
                                     .nickname(member.getNickname())
                                     .name(member.getName())
                                     .likeCnt(paper.getLikeCount())
                                     .commentCnt(paper.getCommentCount())
                                     .scrapCnt(paper.getScrapCount())
                                     .imageUrl(image.getUrl())
                                     .build();
    }

}
