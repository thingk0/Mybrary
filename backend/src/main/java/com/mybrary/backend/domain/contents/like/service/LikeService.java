package com.mybrary.backend.domain.contents.like.service;

public interface LikeService {
     boolean checkIsLiked(Long paperId, Long memberId);
}
