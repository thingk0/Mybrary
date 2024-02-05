package com.mybrary.backend.domain.contents.thread.repository.custom;

import com.mybrary.backend.domain.mybrary.dto.MybraryGetDto;
import java.util.Optional;

public interface ThreadRepositoryCustom {

    Optional<Integer> countMyThread(Long myId);

}
