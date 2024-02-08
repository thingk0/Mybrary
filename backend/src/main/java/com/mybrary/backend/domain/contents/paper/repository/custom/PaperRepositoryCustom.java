package com.mybrary.backend.domain.contents.paper.repository.custom;

import com.mybrary.backend.domain.contents.paper.dto.GetFollowingPaperDto;
import com.mybrary.backend.domain.contents.paper.dto.PaperGetDto;
import java.util.List;

public interface PaperRepositoryCustom {

    List<GetFollowingPaperDto> getFollowingPaperDtoResults(Long threadId);

    Long deletePaperByThreadsId(Long threadId);
    List<PaperGetDto> getPaperGetDto(Long threadId);
}
