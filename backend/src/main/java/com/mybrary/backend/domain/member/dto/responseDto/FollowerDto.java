package com.mybrary.backend.domain.member.dto.responseDto;

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
public class FollowerDto {

    /**
     *  다른사람의 팔로워 목록 조회
     *  isFollowed - 내가 팔로우 했는지 여부 포함
     */

    private Long memberId;
    private String name;
    private String nickname;
    private Long profileImageId;
    private String profileImageUrl;
    private int followStatus;

    public FollowerDto(Long memberId, String name, String nickname, Long profileImageId, String profileImageUrl) {
        this.memberId = memberId;
        this.name = name;
        this.nickname = nickname;
        this.profileImageId = profileImageId;
        this.profileImageUrl = profileImageUrl;
    }
}
