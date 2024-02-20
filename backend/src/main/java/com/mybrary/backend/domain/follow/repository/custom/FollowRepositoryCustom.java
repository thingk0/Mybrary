package com.mybrary.backend.domain.follow.repository.custom;

import com.mybrary.backend.domain.follow.entity.Follow;
import java.util.Optional;

public interface FollowRepositoryCustom {

    Optional<Integer> countMyFollower(Long myId);

    Optional<Integer> countMyFollowing(Long myId);

    Optional<Follow> findFollow(Long requesterId, Long targetId);
}
