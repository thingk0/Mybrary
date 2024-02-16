package com.mybrary.backend.domain.member.dto.login;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInfo {

    private Long memberId;
    private String email;
    private String nickname;
    private String profileImageUrl;
}

