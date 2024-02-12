package com.mybrary.backend.domain.member.dto.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfo {

    private Long memberId;
    private String email;
    private String nickname;
    private String profileImageUrl;
}

