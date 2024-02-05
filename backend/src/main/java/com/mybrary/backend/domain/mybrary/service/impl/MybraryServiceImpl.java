package com.mybrary.backend.domain.mybrary.service.impl;

import com.mybrary.backend.domain.book.repository.BookRepository;
import com.mybrary.backend.domain.contents.thread.repository.ThreadRepository;
import com.mybrary.backend.domain.follow.repository.FollowRepository;
import com.mybrary.backend.domain.member.repository.MemberRepository;
import com.mybrary.backend.domain.member.service.MemberService;
import com.mybrary.backend.domain.mybrary.dto.MybraryGetDto;
import com.mybrary.backend.domain.mybrary.dto.MybraryOtherGetDto;
import com.mybrary.backend.domain.mybrary.dto.MybraryUpdateDto;
import com.mybrary.backend.domain.mybrary.entity.Mybrary;
import com.mybrary.backend.domain.mybrary.repository.MybraryRepository;
import com.mybrary.backend.domain.mybrary.service.MybraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MybraryServiceImpl implements MybraryService {

    private final MemberService memberService;
    private final MybraryRepository mybraryRepository;
    private final ThreadRepository threadRepository;
    private final BookRepository bookRepository;
    private final FollowRepository followRepository;

    @Override
    public MybraryGetDto getMybrary(String email) {
        Long myId = memberService.findMember(email).getId();
        MybraryGetDto mybrary = mybraryRepository.getMybrary(myId);
        mybrary.setThreadCount(threadRepository.countMyThread(mybrary.getMybraryId()).orElse(0));
        mybrary.setBookCount(bookRepository.countMyBook(mybrary.getBookShelfId()).orElse(0));
        mybrary.setFollowerCount(followRepository.countMyFollower(myId).orElse(0));
        mybrary.setFollowingCount(followRepository.countMyFollowing(myId).orElse(0));
        return mybrary;
    }

    @Override
    public MybraryOtherGetDto getOtherMybrary(String myEmail, Long memberid) {
        Long myId = memberService.findMember(myEmail).getId();
        MybraryOtherGetDto mybrary = mybraryRepository.getOtherMybrary(memberid);
        mybrary.setThreadCount(threadRepository.countMyThread(mybrary.getMybraryId()).orElse(0));
        mybrary.setBookCount(bookRepository.countMyBook(mybrary.getBookShelfId()).orElse(0));
        mybrary.setFollowerCount(followRepository.countMyFollower(memberid).orElse(0));
        mybrary.setFollowingCount(followRepository.countMyFollowing(memberid).orElse(0));
        if(followRepository.findFollow(myId, memberid)!=null){
            mybrary.setFollowed(true);
        }
        return mybrary;
    }

    @Transactional
    @Override
    public void updateMybrary(MybraryUpdateDto mybrary) {
        Mybrary oldMybrary = mybraryRepository.findById(mybrary.getMybraryId()).get();
        oldMybrary.setBackgroundColor(mybrary.getBackgroundColor());
        oldMybrary.setDeskColor(mybrary.getDeskColor());
        oldMybrary.setBookshelfColor(mybrary.getBookshelfColor());
        oldMybrary.setEaselColor(mybrary.getEaselColor());
        /* 액자이미지 수정 작성해야 함 */

    }
}
