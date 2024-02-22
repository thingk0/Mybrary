package com.mybrary.backend.domain.contents.like.repository.impl;

import static com.mybrary.backend.domain.contents.like.entity.QLike.like;
import static com.mybrary.backend.domain.contents.paper.entity.QPaper.paper;

import com.mybrary.backend.domain.contents.like.entity.Like;
import com.mybrary.backend.domain.contents.like.repository.custom.LikeRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepositoryCustom {

    private final JPAQueryFactory query;

    public Optional<Like> isLikedPaper(Long memberId, Long paperId) {
        return Optional.ofNullable(query.select(like)
                                        .from(like)
                                        .where(like.member.id.eq(memberId).and(like.paper.id.eq(paperId)))
                                        .fetchOne());
    }

    @Override
    public Optional<Integer> getLikeCount(Long paperId) {
        return Optional.ofNullable(query.select(like.count().intValue())
                                        .from(like)
                                        .where(like.paper.id.eq(paperId))
                                        .fetchOne()
        );
    }

    @Override
    public Optional<Integer> getThreadtLikeCount(Long threadId) {
        return Optional.ofNullable(query.select(like.count().intValue())
                                        .from(like)
                                        .leftJoin(paper).on(like.paper.id.eq(paper.id))
                                        .where(paper.thread.id.eq(threadId))
                                        .fetchOne()
        );
    }
}
