package com.mybrary.backend.domain.contents.thread.service;

import com.mybrary.backend.domain.contents.thread.responseDto.DeleteThreadDto;
import com.mybrary.backend.domain.contents.thread.responseDto.GetThreadDto;
import com.mybrary.backend.domain.contents.thread.responseDto.ThreadInfoGetDto;
import com.mybrary.backend.domain.contents.thread.requestDto.ThreadPostDto;
import com.mybrary.backend.domain.contents.thread.requestDto.ThreadUpdateDto;
import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ThreadService {

    Long createThread(Long myId, ThreadPostDto threadPostDto) throws IOException;

    Long updateThread(Long myId, ThreadUpdateDto threadUpdateDto);

    DeleteThreadDto deleteThread(Long myId, Long threadId);

    List<GetThreadDto> getMainAllThread(Long myId, Pageable pageable);

    List<ThreadInfoGetDto> getMyAllThread(Long myId, Pageable pageable);

    List<ThreadInfoGetDto> getOtherAllThread(Long memberId, Pageable pageable);

    Object getThread(Long memberId, Long threadId);
}
