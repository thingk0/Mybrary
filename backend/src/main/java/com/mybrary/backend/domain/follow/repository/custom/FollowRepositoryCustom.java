package com.mybrary.backend.domain.follow.repository.custom;

import java.util.Optional;

public interface FollowRepositoryCustom {

    Optional<Integer> countMyFollower(Long myId);

    Optional<Integer> countMyFollowing(Long myId);
}
