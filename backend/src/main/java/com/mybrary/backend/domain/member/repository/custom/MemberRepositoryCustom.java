package com.mybrary.backend.domain.member.repository.custom;

import com.mybrary.backend.domain.follow.entity.Follow;
import com.mybrary.backend.domain.member.dto.responseDto.FollowerDto;
import com.mybrary.backend.domain.member.dto.responseDto.FollowingDto;
import com.mybrary.backend.domain.member.dto.responseDto.MemberGetDto;
import com.mybrary.backend.domain.member.dto.responseDto.MemberInfoDto;
import com.mybrary.backend.domain.member.dto.responseDto.MyFollowerDto;
import com.mybrary.backend.domain.member.dto.responseDto.MyFollowingDto;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.notification.entity.Notification;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

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

    Optional<Member> findByCategoryId(Long categoryId);

    Optional<List<MemberGetDto>> searchAcoountByKo(Long myId, String keyword, Pageable page);

    Optional<List<MemberGetDto>> searchAcoountByEn(Long myId, String keyword, Pageable page);
}
