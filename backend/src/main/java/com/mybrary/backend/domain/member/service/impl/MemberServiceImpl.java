package com.mybrary.backend.domain.member.service.impl;

import com.mybrary.backend.domain.member.dto.LoginRequestDto;
import com.mybrary.backend.domain.member.dto.SignupRequestDto;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.member.repository.MemberRepository;
import com.mybrary.backend.domain.member.service.MemberService;
import com.mybrary.backend.global.util.CookieUtil;
import com.mybrary.backend.global.exception.member.DuplicateEmailException;
import com.mybrary.backend.global.exception.member.EmailNotFoundException;
import com.mybrary.backend.global.exception.member.InvalidLoginAttemptException;
import com.mybrary.backend.global.exception.member.PasswordMismatchException;
import com.mybrary.backend.global.format.ErrorCode;
import com.mybrary.backend.global.jwt.JwtProvider;
import com.mybrary.backend.global.jwt.dto.TokenInfo;
import com.mybrary.backend.global.jwt.repository.RefreshTokenRepository;
import com.mybrary.backend.global.jwt.token.RefreshToken;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final JwtProvider jwtProvider;
    private final CookieUtil cookieUtil;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public Long create(SignupRequestDto requestDto) {

        /* 비밀번호 불일치 */
        if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
            throw new PasswordMismatchException(ErrorCode.MEMBER_PASSWORD_MISMATCH);
        }

        /* 이메일 중복 검증 */
        memberRepository.findByEmail(requestDto.getEmail())
                        .ifPresent(member -> {
                            throw new DuplicateEmailException(ErrorCode.MEMBER_EMAIL_DUPLICATED);
                        });

        Member member = Member.of(requestDto, passwordEncoder.encode(requestDto.getPassword()));
        memberRepository.save(member);
        return member.getId();
    }

    @Override
    @Transactional
    public void login(LoginRequestDto requestDto, HttpServletResponse response) {

        Member member = memberRepository.findByEmail(requestDto.getEmail())
                                        .orElseThrow(
                                            () -> new InvalidLoginAttemptException(
                                                ErrorCode.MEMBER_LOGIN_FAILED));

        if (!validatePassword(requestDto.getPassword(), member.getPassword())) {
            throw new PasswordMismatchException(ErrorCode.MEMBER_LOGIN_FAILED);
        }

        refreshTokenRepository.findByValue(member.getEmail())
                              .ifPresent(refreshTokenRepository::delete);

        TokenInfo tokenInfo = jwtProvider.generateTokenInfo(member.getEmail());
        refreshTokenRepository.save(RefreshToken.builder()
                                                .key(tokenInfo.getRefreshToken())
                                                .value(tokenInfo.getEmail())
                                                .accessToken(tokenInfo.getAccessToken())
                                                .build());

        cookieUtil.addCookie("Access-Token", tokenInfo.getAccessToken(),
                             jwtProvider.getACCESS_TOKEN_TIME() + 60,
                             response);
    }

    @Override
    public Member findMember(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
            () -> new EmailNotFoundException(ErrorCode.MEMBER_EMAIL_NOT_FOUND)
        );
    }

    private boolean validatePassword(String input, String encoded) {
        return passwordEncoder.matches(input, encoded);
    }

}
