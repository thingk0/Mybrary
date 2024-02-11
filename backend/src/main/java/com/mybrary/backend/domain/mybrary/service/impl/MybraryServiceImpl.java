
package com.mybrary.backend.domain.mybrary.service.impl;

import com.mybrary.backend.domain.book.repository.BookRepository;
import com.mybrary.backend.domain.contents.thread.repository.ThreadRepository;
import com.mybrary.backend.domain.follow.entity.Follow;
import com.mybrary.backend.domain.follow.repository.FollowRepository;
import com.mybrary.backend.domain.image.entity.Image;
import com.mybrary.backend.domain.image.repository.ImageRepository;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.member.repository.MemberRepository;
import com.mybrary.backend.domain.member.service.MemberService;
import com.mybrary.backend.domain.mybrary.dto.MybraryGetDto;
import com.mybrary.backend.domain.mybrary.dto.MybraryOtherGetDto;
import com.mybrary.backend.domain.mybrary.dto.MybraryUpdateDto;
import com.mybrary.backend.domain.mybrary.entity.Mybrary;
import com.mybrary.backend.domain.mybrary.repository.MybraryRepository;
import com.mybrary.backend.domain.mybrary.service.MybraryService;
import com.mybrary.backend.global.exception.image.ImageNotFoundException;
import com.mybrary.backend.global.exception.member.MemberNotFoundException;
import com.mybrary.backend.global.exception.mybrary.MybraryAccessDeniedException;
import com.mybrary.backend.global.exception.mybrary.MybraryNotFoundException;
import com.mybrary.backend.global.exception.mybrary.NotMybraryException;
import com.mybrary.backend.global.exception.thread.ThreadAccessDeniedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Log4j2
@RequiredArgsConstructor
@Service
public class MybraryServiceImpl implements MybraryService {

    private final MemberService memberService;
    private final MybraryRepository mybraryRepository;
    private final ThreadRepository threadRepository;
    private final BookRepository bookRepository;
    private final FollowRepository followRepository;
    private final ImageRepository imageRepository;
    private final MemberRepository memberRepository;

    @Override
    public MybraryGetDto getMybrary(String email) {
        Long myId = memberService.findMember(email).getId();
        MybraryGetDto mybrary = mybraryRepository.getMybrary(myId).orElseThrow(MybraryNotFoundException::new);
        mybrary.setThreadCount(threadRepository.countMyThread(mybrary.getMybraryId()).orElse(0));
        mybrary.setBookCount(bookRepository.countMyBook(mybrary.getBookShelfId()).orElse(0));
        mybrary.setFollowerCount(followRepository.countMyFollower(myId).orElse(0));
        mybrary.setFollowingCount(followRepository.countMyFollowing(myId).orElse(0));
        return mybrary;
    }

    @Override
    public MybraryOtherGetDto getOtherMybrary(String myEmail, Long memberId) {

        /* 마이브러리 접근 권한 판단 */
        Long myId = memberRepository.searchByEmail(myEmail).orElseThrow(MemberNotFoundException::new).getId();
        Member Owner = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        if(!Owner.isProfilePublic()){
            Follow follow = followRepository.findFollow(myId, Owner.getId()).orElseThrow(MybraryAccessDeniedException::new);
        }

        MybraryOtherGetDto mybrary = mybraryRepository.getOtherMybrary(memberId).orElseThrow(MybraryNotFoundException::new);
        mybrary.setThreadCount(threadRepository.countMyThread(mybrary.getMybraryId()).orElse(0));
        mybrary.setBookCount(bookRepository.countMyBook(mybrary.getBookShelfId()).orElse(0));
        mybrary.setFollowerCount(followRepository.countMyFollower(memberId).orElse(0));
        mybrary.setFollowingCount(followRepository.countMyFollowing(memberId).orElse(0));
        if(memberRepository.isFollowed(myId, memberId).orElse(null)!=null){
            mybrary.setFollowStatus(3);
        } else if(memberRepository.isRequested(myId, memberId).orElse(null)!=null){
            mybrary.setFollowStatus(2);
        } else{
            mybrary.setFollowStatus(1);
        }
        return mybrary;
    }

    @Transactional
    @Override
    public void updateMybrary(String email, MybraryUpdateDto mybrary) {

        Member member = memberService.findMember(email);
        Mybrary oldMybrary = mybraryRepository.findById(mybrary.getMybraryId()).orElseThrow(MybraryNotFoundException::new);
        if (!oldMybrary.getMember().getId().equals(member.getId())) {
            throw new NotMybraryException();
        }

        Image image = imageRepository.findById(mybrary.getFrameImageId()).orElseThrow(ImageNotFoundException::new);

        oldMybrary.setPhotoFrameImage(image);
        oldMybrary.setBackgroundColor(mybrary.getBackgroundColor());
        oldMybrary.setDeskColor(mybrary.getDeskColor());
        oldMybrary.setBookshelfColor(mybrary.getBookshelfColor());
        oldMybrary.setEaselColor(mybrary.getEaselColor());

    }
}
