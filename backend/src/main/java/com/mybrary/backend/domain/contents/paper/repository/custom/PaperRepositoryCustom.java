package com.mybrary.backend.domain.contents.paper.repository.custom;

import com.mybrary.backend.domain.contents.paper.dto.GetFollowingPaperDto;
import java.util.List;

public interface PaperRepositoryCustom {

    List<GetFollowingPaperDto> getFollowingPaperDtoResults(Long threadId);

    Long deletePapersByThreadId(Long threadId);
}
