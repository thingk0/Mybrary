package com.mybrary.backend.domain.contents.thread.repository.custom;

import com.mybrary.backend.domain.contents.thread.responseDto.GetThreadDto;
import com.mybrary.backend.domain.contents.thread.responseDto.ThreadInfoGetDto;
import com.mybrary.backend.domain.contents.thread.responseDto.ThreadShareGetDto;
import com.mybrary.backend.domain.contents.thread.entity.Thread;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface ThreadRepositoryCustom {

    Optional<Integer> countMyThread(Long myId);

    Optional<Thread> findByThreadId(Long threadId);

    /* 나를 포함하여 내가 팔로잉중인 회원들의 쓰레드 n개 조회  */
    List<GetThreadDto> getFollowingThreadDtoResults(Long memberId, Pageable pageable);

    /* 나와 내가 팔로잉중인 회원을 제외한 회원들의 랜덤 쓰레드 n개 조회  */
    List<GetThreadDto> getRandomThreadDtoResults(Long memberId, Pageable pageable, int count);

    List<ThreadInfoGetDto> getSimpleThreadDtoResults(Long memberId, Pageable pageable);

    ThreadShareGetDto getThreadShare(Long threadId);
}
