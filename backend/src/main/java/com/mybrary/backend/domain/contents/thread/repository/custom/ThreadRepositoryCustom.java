package com.mybrary.backend.domain.contents.thread.repository.custom;

import com.mybrary.backend.domain.contents.thread.dto.responseDto.GetThreadDto;
import com.mybrary.backend.domain.contents.thread.dto.responseDto.ThreadInfoGetDto;
import com.mybrary.backend.domain.contents.thread.dto.responseDto.ThreadSearchGetDto;
import com.mybrary.backend.domain.contents.thread.dto.responseDto.ThreadShareGetDto;
import com.mybrary.backend.domain.contents.thread.entity.Thread;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface ThreadRepositoryCustom {

    Optional<Integer> countMyThread(Long myId);

    Optional<Thread> findByThreadId(Long threadId);

    /* 나를 포함하여 내가 팔로잉중인 회원들의 쓰레드 n개 조회  */
    Optional<List<GetThreadDto>> getFollowingThreadDtoResults(Long memberId, Pageable pageable);

    /* 나와 내가 팔로잉중인 회원을 제외한 회원들의 랜덤 쓰레드 n개 조회  */
    Optional<List<GetThreadDto>> getRandomThreadDtoResults(Long memberId, Pageable pageable);

    /* 이 두개는 쓰레드 목록 조회에 사용됨 */
        Optional<List<Thread>> getThreadsByMemberId(Long memberId, Pageable pageable);
        ThreadInfoGetDto getSimpleThreadDtoResult(Long threadId);
    /* */
//    List<ThreadInfoGetDto> getSimpleThreadDtoResults(Long memberId, Pageable pageable);

    ThreadShareGetDto getThreadShare(Long threadId);

    Optional<List<ThreadSearchGetDto>> searchThreadByKeyword(Long myId, String keyword, Pageable page);

    Optional<GetThreadDto> getOneThread(Long threadId);
}
