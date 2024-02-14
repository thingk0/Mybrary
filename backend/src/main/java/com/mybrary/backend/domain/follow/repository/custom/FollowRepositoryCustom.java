package com.mybrary.backend.domain.follow.repository.custom;

import com.mybrary.backend.domain.follow.entity.Follow;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FollowRepositoryCustom {

    Optional<Integer> countMyFollower(Long myId);

    Optional<Integer> countMyFollowing(Long myId);

    Optional<Follow> findFollow(Long myId, Long memberId);
}
