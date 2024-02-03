package com.mybrary.backend.domain.member.service;

import com.mybrary.backend.domain.member.dto.FollowerDto;
import com.mybrary.backend.domain.member.dto.FollowingDto;
import com.mybrary.backend.domain.member.dto.LoginRequestDto;
import com.mybrary.backend.domain.member.dto.MyFollowerDto;
import com.mybrary.backend.domain.member.dto.MyFollowingDto;
import com.mybrary.backend.domain.member.dto.SignupRequestDto;
import com.mybrary.backend.domain.member.entity.Member;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public interface MemberService {

    Long create(SignupRequestDto requestDto);

    void login(LoginRequestDto requestDto, HttpServletResponse httpServletResponse);

    Member findMember(String email);

    List<MyFollowingDto> getAllMyFollowing(Long myId);

    List<MyFollowerDto> getAllMyFollower(Long myId);

    List<FollowingDto> getAllFollowing(Long myId, Long memberId);

    List<FollowerDto> getAllFollower(Long myId, Long memberId);

    void follow(Long myId, Long memberId);

    void unfollow(Long myId, Long memberId);

    void deleteFollower(Long myId, Long memberId);
}
