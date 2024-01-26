package com.mybrary.backend.domain.member.service.impl;

import com.mybrary.backend.domain.member.dto.SignupRequestDto;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.member.repository.MemberRepository;
import com.mybrary.backend.domain.member.service.MemberService;
import com.mybrary.backend.global.exception.DuplicateEmailException;
import com.mybrary.backend.global.exception.PasswordMismatchException;
import com.mybrary.backend.global.format.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
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

        /* 비밀번호 암호화  */
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        Member member = Member.from(requestDto);
        memberRepository.save(member);
        return member.getId();
    }

}
