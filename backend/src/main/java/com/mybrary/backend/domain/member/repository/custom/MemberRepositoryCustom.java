package com.mybrary.backend.domain.member.repository.custom;

import com.mybrary.backend.domain.follow.entity.Follow;
import com.mybrary.backend.domain.member.dto.responseDto.FollowerDto;
import com.mybrary.backend.domain.member.dto.responseDto.FollowingDto;
import com.mybrary.backend.domain.member.dto.responseDto.MemberInfoDto;
import com.mybrary.backend.domain.member.dto.responseDto.MyFollowerDto;
import com.mybrary.backend.domain.member.dto.responseDto.MyFollowingDto;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.notification.entity.Notification;
import java.util.List;
import java.util.Optional;

public interface MemberRepositoryCustom {

    Optional<Member> searchByEmail(String email);

    boolean isNicknameDuplicate(String email);

    Optional<MemberInfoDto> getMemberInfo(Long myId);

    Optional<List<MyFollowingDto>> getAllMyFollowing(Long myId);

    Optional<List<MyFollowerDto>> getAllMyFollower(Long myId);

    Optional<List<FollowingDto>> getAllFollowing(Long memberId);

    Optional<List<FollowerDto>> getAllFollower(Long memberId);

    Optional<Follow> isFollowed(Long myId, Long memberId);

    Optional<Notification> isRequested(Long myId, Long memberId);
}
