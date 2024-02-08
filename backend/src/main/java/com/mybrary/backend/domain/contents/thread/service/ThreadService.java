package com.mybrary.backend.domain.contents.thread.service;

import com.mybrary.backend.domain.contents.thread.responseDto.DeleteThreadDto;
import com.mybrary.backend.domain.contents.thread.responseDto.GetThreadDto;
import com.mybrary.backend.domain.contents.thread.responseDto.ThreadInfoGetDto;
import com.mybrary.backend.domain.contents.thread.requestDto.ThreadPostDto;
import com.mybrary.backend.domain.contents.thread.requestDto.ThreadUpdateDto;
import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ThreadService {

    Long createThread(ThreadPostDto threadPostDto) throws IOException;

    Long updateThread(ThreadUpdateDto threadUpdateDto);

    DeleteThreadDto deleteThread(Long threadId);

    List<GetThreadDto> getMainAllThread(Long memberId, Pageable pageable);

    List<ThreadInfoGetDto> getMyAllThread(Long memberId, Pageable pageable);

    List<ThreadInfoGetDto> getOtherAllThread(Long memberId, Pageable pageable);
}
