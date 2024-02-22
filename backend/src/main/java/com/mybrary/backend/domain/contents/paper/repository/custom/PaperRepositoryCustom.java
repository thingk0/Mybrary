package com.mybrary.backend.domain.contents.paper.repository.custom;

import static com.mybrary.backend.domain.contents.thread.entity.QThread.thread;
import static com.mybrary.backend.domain.follow.entity.QFollow.follow;
import static com.mybrary.backend.domain.image.entity.QImage.image;
import static com.mybrary.backend.domain.member.entity.QMember.member;
import static com.mybrary.backend.domain.mybrary.entity.QMybrary.mybrary;

import com.mybrary.backend.domain.contents.paper.dto.responseDto.GetFollowingPaperDto;
import com.mybrary.backend.domain.contents.paper.dto.responseDto.PaperGetDto;
import com.mybrary.backend.domain.contents.paper.dto.responseDto.PaperInBookGetDto;
import com.mybrary.backend.domain.contents.thread.dto.responseDto.GetThreadDto;
import com.mybrary.backend.domain.member.dto.responseDto.MemberInfoDto;
import com.mybrary.backend.domain.search.dto.SearchPaperResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

public interface PaperRepositoryCustom {

    Page<SearchPaperResponseDto> fetchPaperSearchList(List<Long> paperIdList, Pageable pageable);

    Optional<List<GetFollowingPaperDto>> getFollowingPaperDtoResults(Long threadId);

    Long deletePaperByThreadId(Long threadId);
    Optional<List<PaperGetDto>> getPaperGetDto(Long threadId);

    Optional<List<PaperInBookGetDto>> getPaperList(Long bookId);

    Optional<MemberInfoDto> getWriter(Long paperId);

    Optional<Long> getImageUrl(Long paperId, int seq);

    Optional<Long> getThreadIdByPaperId(Long paperId);
}
