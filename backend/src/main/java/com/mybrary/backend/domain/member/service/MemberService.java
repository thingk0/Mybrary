package com.mybrary.backend.domain.member.service;

import com.mybrary.backend.domain.member.dto.LoginRequestDto;
import com.mybrary.backend.domain.member.dto.SignupRequestDto;
import com.mybrary.backend.domain.member.entity.Member;
import jakarta.servlet.http.HttpServletResponse;

public interface MemberService {

    Long create(SignupRequestDto requestDto);

    void login(LoginRequestDto requestDto, HttpServletResponse httpServletResponse);

    Member findMember(String email);
}
