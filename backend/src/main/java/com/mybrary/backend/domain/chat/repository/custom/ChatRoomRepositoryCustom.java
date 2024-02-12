package com.mybrary.backend.domain.chat.repository.custom;

import com.mybrary.backend.domain.chat.dto.responseDto.ChatRoomResponseDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChatRoomRepositoryCustom {

    Optional<List<Long>> chatRoomIdList2(Long myId, Pageable page);

    Page<ChatRoomResponseDto> fetchMyChatRoomList(String email, Pageable pageable);
}
