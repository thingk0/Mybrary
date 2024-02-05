package com.mybrary.backend.domain.member.service.impl;

import com.mybrary.backend.domain.follow.entity.Follow;
import com.mybrary.backend.domain.follow.repository.FollowRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private final FollowRepository followRepository;
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


    @Override
    public List<MyFollowingDto> getAllMyFollowing(Long myId) {

        List<Member> myFollowing = memberRepository.getAllFollowing(myId);

        List<MyFollowingDto> list = new ArrayList<>();
        for (Member member : myFollowing) {
            list.add(new MyFollowingDto(member.getId(), member.getName(), member.getNickname(), null));
        }

        return list;
    }

    @Override
    public List<MyFollowerDto> getAllMyFollower(Long myId) {

        List<Member> myFollower = memberRepository.getAllFollower(myId);

        List<MyFollowerDto> list = new ArrayList<>();
        for (Member member : myFollower) {
            Follow follow = memberRepository.isFollowed(myId, member.getId());
            boolean isFollowed = false;
            if (follow != null) {
                isFollowed = true;
            }
            list.add(new MyFollowerDto(member.getId(), member.getName(), member.getNickname(), null, isFollowed));
        }

        return list;
    }

    @Override
    public List<FollowingDto> getAllFollowing(Long myId, Long memberId) {

        List<Member> Following = memberRepository.getAllFollowing(memberId);

        List<FollowingDto> list = new ArrayList<>();
        for (Member member : Following) {
            Follow follow = memberRepository.isFollowed(myId, member.getId());
            boolean isFollowed = false;
            if (follow != null) {
                isFollowed = true;
            }
            list.add(new FollowingDto(member.getId(), member.getName(), member.getNickname(), null, isFollowed));
        }

        return list;

    }

    @Override
    public List<FollowerDto> getAllFollower(Long myId, Long memberId) {
        List<Member> myFollower = memberRepository.getAllFollower(memberId);

        List<FollowerDto> list = new ArrayList<>();
        for (Member member : myFollower) {
            Follow follow = memberRepository.isFollowed(myId, member.getId());
            boolean isFollowed = false;
            if (follow != null) {
                isFollowed = true;
            }
            list.add(new FollowerDto(member.getId(), member.getName(), member.getNickname(), null, isFollowed));
        }

        return list;
    }

    @Override
    public void follow(Long myId, Long memberId) {
        Member me = memberRepository.findById(myId).get();
        Member you = memberRepository.findById(memberId).get();
        Follow follow = Follow.builder()
                              .following(you)
                              .follower(me)
                              .build();
        followRepository.save(follow);
    }

    @Transactional
    @Override
    public void unfollow(Long myId, Long memberId) {
        Follow follow = followRepository.findFollow(myId, memberId);
        follow.setDeleted(true);

    }

    @Transactional
    @Override
    public void deleteFollower(Long myId, Long memberId) {
        Follow follow = followRepository.findFollow(memberId, myId);
        follow.setDeleted(true);
    }

    @Transactional
    @Override
    public void updateProfile(MemberUpdateDto member) {
        Member me = memberRepository.findById(member.getMemberId()).get();
        me.updateNickname(member.getNickname());
        me.updateIntro(member.getIntro());
        me.updateIsProfilePublic(member.isProfilePublic());
        me.updateIsNotifyEnable(member.isNotifyEnable());
        /* 프로필이미지 처리 작성해야함 */
    }

    @Transactional
    @Override
    public void updatePassword(Long myId, PasswordUpdateDto password) {

        /* 비밀번호 불일치 */
        if (!password.getPassword().equals(password.getPasswordConfirm())) {
            throw new PasswordMismatchException(ErrorCode.MEMBER_PASSWORD_MISMATCH);
        }

        Member me = memberRepository.findById(myId).get();
        me.updatePassword(passwordEncoder.encode(password.getPassword()));

    }

    @Transactional
    @Override
    public void secession(SecessionRequestDto secession) {

        Member member = memberRepository.findByEmail(secession.getEmail())
                                        .orElseThrow(
                                            () -> new InvalidLoginAttemptException(
                                                ErrorCode.MEMBER_LOGIN_FAILED));

        if (!validatePassword(secession.getPassword(), member.getPassword())) {
            throw new PasswordMismatchException(ErrorCode.MEMBER_LOGIN_FAILED);
        }

        memberRepository.delete(member);

    }
}
