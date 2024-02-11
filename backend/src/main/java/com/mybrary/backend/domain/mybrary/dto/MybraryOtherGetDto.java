package com.mybrary.backend.domain.mybrary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MybraryOtherGetDto {

    /**
     *  다른사람의 마이브러리 정보 요청
     *  isFollowed - 팔로우했는지 여부도 포함
     */

    private Long mybraryId;
    private Long frameImageId;
    private String frameImageUrl;
    private int backgroundColor;
    private int deskColor;
    private int bookshelfColor;
    private int easelColor;
    private Long memberId;
    private String email;
    private String name;
    private String nickname;
    private String intro;
    private Long profileImageId;
    private String profileImageUrl;
    private boolean isNotifyEnable;
    private boolean isProfilePublic;
    private int threadCount;
    private int bookCount;
    private int followerCount;
    private int followingCount;
    private int followStatus;
    private Long bookShelfId;
    private Long rollingPaperId;

    public MybraryOtherGetDto(Long mybraryId, Long frameImageId, String frameImageUrl, int backgroundColor, int deskColor, int bookshelfColor,
                         int easelColor, Long memberId, String email, String name, String nickname, String intro, Long profileImageId, String profileImageUrl,
                         boolean isNotifyEnable, boolean isProfilePublic, Long bookShelfId, Long rollingPaperId) {
        this.mybraryId = mybraryId;
        this.frameImageId = frameImageId;
        this.frameImageUrl = frameImageUrl;
        this.backgroundColor = backgroundColor;
        this.deskColor = deskColor;
        this.bookshelfColor = bookshelfColor;
        this.easelColor = easelColor;
        this.memberId = memberId;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.intro = intro;
        this.profileImageId = profileImageId;
        this.profileImageUrl = profileImageUrl;
        this.isNotifyEnable = isNotifyEnable;
        this.isProfilePublic = isProfilePublic;
        this.bookShelfId = bookShelfId;
        this.rollingPaperId = rollingPaperId;
    }
}
