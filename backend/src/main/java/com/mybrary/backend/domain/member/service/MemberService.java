package com.mybrary.backend.domain.member.service;

import com.mybrary.backend.domain.member.dto.LoginRequestDto;
import com.mybrary.backend.domain.member.dto.SignupRequestDto;
import jakarta.servlet.http.HttpServletResponse;

public interface MemberService {

    Long create(SignupRequestDto requestDto);

    void login(LoginRequestDto requestDto, HttpServletResponse httpServletResponse);
}
