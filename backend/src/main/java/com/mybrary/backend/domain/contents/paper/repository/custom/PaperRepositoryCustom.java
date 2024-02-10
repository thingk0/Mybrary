package com.mybrary.backend.domain.contents.paper.repository.custom;

import com.mybrary.backend.domain.contents.paper.dto.responseDto.GetFollowingPaperDto;
import com.mybrary.backend.domain.contents.paper.dto.responseDto.PaperGetDto;
import com.mybrary.backend.domain.contents.paper.dto.responseDto.PaperInBookGetDto;
import com.mybrary.backend.domain.member.dto.responseDto.MemberInfoDto;
import java.util.List;
import java.util.Optional;

public interface PaperRepositoryCustom {

    Optional<List<GetFollowingPaperDto>> getFollowingPaperDtoResults(Long threadId);

    Long deletePaperByThreadId(Long threadId);
    Optional<List<PaperGetDto>> getPaperGetDto(Long threadId);

    Optional<List<PaperInBookGetDto>> getPaperList(Long bookId);

    Optional<MemberInfoDto> getWriter(Long paperId);

    Optional<Long> getImageUrl(Long paperId, int seq);

    Optional<Long> getThreadIdByPaperId(Long paperId);
}
