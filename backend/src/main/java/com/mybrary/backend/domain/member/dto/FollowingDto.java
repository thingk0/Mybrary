package com.mybrary.backend.domain.member.dto;

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
public class FollowingDto {

    /**
     *  다른사람의 팔로잉 목록 조회
     *  isFollowed - 내가 팔로우 했는지 여부 포함
     */

    private Long memberId;
    private String name;
    private String nickname;
    private String profileImageUrl;
    private boolean isFollowed;

}
