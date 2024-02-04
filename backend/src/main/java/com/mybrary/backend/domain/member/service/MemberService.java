package com.mybrary.backend.domain.member.service;

import com.mybrary.backend.domain.member.dto.FollowerDto;
import com.mybrary.backend.domain.member.dto.FollowingDto;
import com.mybrary.backend.domain.member.dto.LoginRequestDto;
import com.mybrary.backend.domain.member.dto.MemberUpdateDto;
import com.mybrary.backend.domain.member.dto.MyFollowerDto;
import com.mybrary.backend.domain.member.dto.MyFollowingDto;
import com.mybrary.backend.domain.member.dto.PasswordUpdateDto;
import com.mybrary.backend.domain.member.dto.SecessionRequestDto;
import com.mybrary.backend.domain.member.dto.SignupRequestDto;
import com.mybrary.backend.domain.member.entity.Member;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public interface MemberService {

    Long create(SignupRequestDto requestDto);

    String login(LoginRequestDto requestDto, HttpServletResponse httpServletResponse);

    Member findMember(String email);

    List<MyFollowingDto> getAllMyFollowing(Long myId);

    List<MyFollowerDto> getAllMyFollower(Long myId);

    List<FollowingDto> getAllFollowing(Long myId, Long memberId);

    List<FollowerDto> getAllFollower(Long myId, Long memberId);

    void follow(Long myId, Long memberId);

    void unfollow(Long myId, Long memberId);

    void deleteFollower(Long myId, Long memberId);

    void updateProfile(MemberUpdateDto member);

    void updatePassword(Long myId, PasswordUpdateDto password);

    void secession(SecessionRequestDto secession);
}
