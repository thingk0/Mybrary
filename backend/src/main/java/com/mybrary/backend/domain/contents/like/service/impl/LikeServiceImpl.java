package com.mybrary.backend.domain.contents.like.service.impl;

import com.mybrary.backend.domain.contents.like.repository.LikeRepository;
import com.mybrary.backend.domain.contents.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    /* 사용자가 해당 paper 에 좋아요를 눌렀는지 여부 판단 */
    @Override
    public boolean checkIsLiked(Long memberId, Long paperId) {
        return likeRepository.isLikedPaper(paperId, memberId).orElse(null) != null;
    }
}
