package com.mybrary.backend.domain.contents.thread.service;

import com.mybrary.backend.domain.contents.thread.dto.DeleteThreadDto;
import com.mybrary.backend.domain.contents.thread.dto.GetThreadDto;
import com.mybrary.backend.domain.contents.thread.dto.ThreadInfoGetDto;
import com.mybrary.backend.domain.contents.thread.dto.ThreadPostDto;
import com.mybrary.backend.domain.contents.thread.dto.ThreadUpdateDto;
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
