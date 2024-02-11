package com.mybrary.backend.domain.contents.like.repository.custom;

import com.mybrary.backend.domain.contents.like.entity.Like;
import java.util.Optional;

public interface LikeRepositoryCustom {
    Optional<Like> isLikedPaper(Long paperId, Long memberId);

    Optional<Integer> getLikeCount(Long paperId);

    Optional<Integer> getThreadtLikeCount(Long threadId);
}
