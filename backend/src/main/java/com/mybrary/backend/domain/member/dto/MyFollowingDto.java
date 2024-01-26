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
public class MyFollowingDto {

    /**
     *  나의 팔로잉 목록 조회
     */

    private String memberId;
    private String name;
    private String nickname;
    private String profileImageUrl;

}
