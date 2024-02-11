package com.mybrary.backend.domain.follow.repository.custom;

import static com.mybrary.backend.domain.follow.entity.QFollow.follow;

import com.mybrary.backend.domain.follow.entity.Follow;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FollowRepositoryImpl implements FollowRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Optional<Integer> countMyFollower(Long myId) {
        return Optional.ofNullable(query.select(follow.count().intValue())
                                       .from(follow)
                                       .where(follow.following.id.eq(myId))
                                       .fetchOne()
        );
    }

    @Override
    public Optional<Integer> countMyFollowing(Long myId) {
        return Optional.ofNullable(query.select(follow.count().intValue())
                                         .from(follow)
                                         .where(follow.follower.id.eq(myId))
                                         .fetchOne()
        );
    }

    @Override
    public Optional<Follow> findFollow(Long myId, Long memberId) {
        return Optional.ofNullable(query.select(follow)
                                       .from(follow)
                                       .where(follow.follower.id.eq(myId).and(follow.following.id.eq(memberId)))
                                       .fetchOne());
    }
}
