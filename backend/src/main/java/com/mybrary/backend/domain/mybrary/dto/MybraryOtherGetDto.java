package com.mybrary.backend.domain.mybrary.dto;

import com.mybrary.backend.domain.member.dto.MemberGetDto;
import com.mybrary.backend.domain.rollingpaper.dto.RollingPaperGetDto;
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
public class MybraryOtherGetDto {

    /**
     *  다른사람의 마이브러리 정보 요청
     *  isFollowed - 팔로우했는지 여부도 포함
     */

    private Long mybraryId;
    private String frameImageUrl;
    private int backgroundColor;
    private int deskColor;
    private int bookshelfColor;
    private int easelColor;
    private Long memberId;
    private String email;
    private String nickname;
    private String intro;
    private String profileImageUrl;
    private boolean isNotifyEnable;
    private boolean isProfilePublic;
    private int threadCount;
    private int bookCount;
    private int followerCount;
    private int followingCount;
    private boolean isFollowed = false;
    private Long bookShelfId;
    private Long rollingPaperId;

    public MybraryOtherGetDto(Long mybraryId, String frameImageUrl, int backgroundColor, int deskColor, int bookshelfColor,
                         int easelColor, Long memberId, String email, String nickname, String intro, String profileImageUrl,
                         boolean isNotifyEnable, boolean isProfilePublic, Long bookShelfId, Long rollingPaperId) {
        this.mybraryId = mybraryId;
        this.frameImageUrl = frameImageUrl;
        this.backgroundColor = backgroundColor;
        this.deskColor = deskColor;
        this.bookshelfColor = bookshelfColor;
        this.easelColor = easelColor;
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
        this.intro = intro;
        this.profileImageUrl = profileImageUrl;
        this.isNotifyEnable = isNotifyEnable;
        this.isProfilePublic = isProfilePublic;
        this.bookShelfId = bookShelfId;
        this.rollingPaperId = rollingPaperId;
    }
}
