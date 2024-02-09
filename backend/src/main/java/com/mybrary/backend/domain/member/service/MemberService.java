package com.mybrary.backend.domain.member.service;

import com.mybrary.backend.domain.member.dto.login.LoginResponseDto;
import com.mybrary.backend.domain.member.dto.requestDto.LoginRequestDto;
import com.mybrary.backend.domain.member.dto.requestDto.MemberUpdateDto;
import com.mybrary.backend.domain.member.dto.requestDto.PasswordUpdateDto;
import com.mybrary.backend.domain.member.dto.requestDto.SecessionRequestDto;
import com.mybrary.backend.domain.member.dto.requestDto.SignupRequestDto;
import com.mybrary.backend.domain.member.dto.responseDto.FollowerDto;
import com.mybrary.backend.domain.member.dto.responseDto.FollowingDto;
import com.mybrary.backend.domain.member.dto.responseDto.MyFollowerDto;
import com.mybrary.backend.domain.member.dto.responseDto.MyFollowingDto;
import com.mybrary.backend.domain.member.entity.Member;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public interface MemberService {

    Long create(SignupRequestDto requestDto);

    LoginResponseDto login(LoginRequestDto requestDto, HttpServletResponse httpServletResponse);

    Member findMember(String email);

    List<MyFollowingDto> getAllMyFollowing(Long myId);

    List<MyFollowerDto> getAllMyFollower(Long myId);

    List<FollowingDto> getAllFollowing(Long myId, Long memberId);

    List<FollowerDto> getAllFollower(Long myId, Long memberId);

    void follow(String email, Long memberId, boolean accept);

    void unfollow(Long myId, Long memberId);

    void deleteFollower(Long myId, Long memberId);

    void followCancel(String email, Long memberId);

    void updateProfile(String email, MemberUpdateDto member);

    void updatePassword(String email, PasswordUpdateDto password);

    void secession(SecessionRequestDto secession);

    boolean checkNicknameDuplication(String nickname);

    String logout(String email, HttpServletResponse servletResponse);

}
