
package com.mybrary.backend.domain.mybrary.service.impl;

import com.mybrary.backend.domain.book.repository.BookRepository;
import com.mybrary.backend.domain.contents.thread.repository.ThreadRepository;
import com.mybrary.backend.domain.follow.repository.FollowRepository;
import com.mybrary.backend.domain.image.entity.Image;
import com.mybrary.backend.domain.image.repository.ImageRepository;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.member.service.MemberService;
import com.mybrary.backend.domain.mybrary.dto.MybraryGetDto;
import com.mybrary.backend.domain.mybrary.dto.MybraryOtherGetDto;
import com.mybrary.backend.domain.mybrary.dto.MybraryUpdateDto;
import com.mybrary.backend.domain.mybrary.entity.Mybrary;
import com.mybrary.backend.domain.mybrary.repository.MybraryRepository;
import com.mybrary.backend.domain.mybrary.service.MybraryService;
import com.mybrary.backend.global.exception.ImageNotFoundException;
import com.mybrary.backend.global.exception.MybraryNotFoundException;
import com.mybrary.backend.global.exception.NotMybraryException;
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
    public MybraryOtherGetDto getOtherMybrary(String myEmail, Long memberid) {
        Long myId = memberService.findMember(myEmail).getId();
        MybraryOtherGetDto mybrary = mybraryRepository.getOtherMybrary(memberid).orElseThrow(MybraryNotFoundException::new);
        mybrary.setThreadCount(threadRepository.countMyThread(mybrary.getMybraryId()).orElse(0));
        mybrary.setBookCount(bookRepository.countMyBook(mybrary.getBookShelfId()).orElse(0));
        mybrary.setFollowerCount(followRepository.countMyFollower(memberid).orElse(0));
        mybrary.setFollowingCount(followRepository.countMyFollowing(memberid).orElse(0));
        if (followRepository.findFollow(myId, memberid) != null) {
            mybrary.setFollowed(true);
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
