package com.mybrary.backend.domain.member.service;

import com.mybrary.backend.domain.member.dto.SignupRequestDto;

public interface MemberService {
    Long create(SignupRequestDto requestDto);
}
