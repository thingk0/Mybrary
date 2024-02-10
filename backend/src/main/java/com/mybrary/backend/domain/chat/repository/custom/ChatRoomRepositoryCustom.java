package com.mybrary.backend.domain.chat.repository.custom;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface ChatRoomRepositoryCustom {

    Optional<List<Long>> chatRoomIdList2(Long myId, Pageable page);
}
