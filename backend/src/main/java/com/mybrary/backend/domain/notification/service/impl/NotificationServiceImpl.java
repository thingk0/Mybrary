package com.mybrary.backend.domain.notification.service.impl;

import com.mybrary.backend.domain.book.entity.Book;
import com.mybrary.backend.domain.book.repository.BookRepository;
import com.mybrary.backend.domain.follow.entity.Follow;
import com.mybrary.backend.domain.follow.repository.FollowRepository;
import com.mybrary.backend.domain.member.dto.responseDto.MemberInfoDto;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.member.repository.MemberRepository;
import com.mybrary.backend.domain.member.service.MemberService;
import com.mybrary.backend.domain.notification.dto.NotificationGetDto;
import com.mybrary.backend.domain.notification.dto.NotificationPostDto;
import com.mybrary.backend.domain.notification.entity.Notification;
import com.mybrary.backend.domain.notification.repository.NotificationRepository;
import com.mybrary.backend.domain.notification.service.NotificationService;
import com.mybrary.backend.global.exception.member.MemberNotFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final FollowRepository followRepository;

    @Transactional(readOnly = true)
    @Override
    public List<NotificationGetDto> getAllNotification(Long myId, Pageable page) {

        // 나의 알림 리스트 조회
        List<Notification> notificationList = notificationRepository.getAllNotification(myId);

        // 알림조회 DTO에 맞는 리스트 생성
        List<NotificationGetDto> list = new ArrayList<>();

        // 알림 개수만큼 반복해서 DTO에 맞게 저장
        for (Notification notify : notificationList) {

            // 알림을 보낸 사람 조회
            Member member = notify.getSender();
            MemberInfoDto sender = new MemberInfoDto(member.getId(), member.getNickname(),
                                                     member.getIntro(), member.getProfileImage().getId(), member.getProfileImage().getUrl());

            // DTO에 맞게 저장
            list.add(new NotificationGetDto(notify.getId(), sender, notify.getNotifyType(),
                                            notify.getBookId(), null, notify.getThreadId(),
                                            notify.getPaperId(), notify.getCommentId(),
                                            notify.getReplyCommentId()));
        }

        return list;
    }

    @Override
    public void deleteNotification(Long notifyId) {

        // 알림 단건 삭제
        notificationRepository.deleteById(notifyId);

    }

    @Override
    public void deleteAllNotification(Long myId) {

        // 회원 Id로 알림 전체 삭제
        notificationRepository.deleteAllByMemberId(myId);

    }

    @Override
    public void saveNotification(NotificationPostDto notification) {

        // 알림 발신자와 수신자 엔티티 조회
        Member sender = memberRepository.findById(notification.getSenderId()).get();
        Member receiver = memberRepository.findById(notification.getSenderId()).get();

        // 알림 엔티티 생성
        Notification newNotification = Notification.builder()
                                                   .sender(sender).receiver(receiver)
                                                   .bookId(notification.getBookId())
                                                   .threadId(notification.getThreadId())
                                                   .paperId(notification.getPaperId())
                                                   .commentId(notification.getCommentId())
                                                   .replyCommentId(notification.getReplyCommentId())
                                                   .build();
        // 알림 저장
        Notification savedNotification = notificationRepository.save(newNotification);

        // 알림 수신자의 알림구독주소에 저장된 알림 객체 반환하기
        MemberInfoDto senderDto = new MemberInfoDto(sender.getId(), sender.getNickname(), sender.getIntro(), sender.getProfileImage().getId(), sender.getProfileImage().getUrl());
        String bookTitle = null;
        if(savedNotification.getBookId() != null){
            Book book = bookRepository.findById(savedNotification.getBookId()).get();
            bookTitle = book.getCoverTitle();
        }
        NotificationGetDto sendNotification = new NotificationGetDto(savedNotification.getId(), senderDto, savedNotification.getNotifyType(), savedNotification.getBookId(), bookTitle, savedNotification.getThreadId(), savedNotification.getPaperId(), savedNotification.getCommentId(), savedNotification.getReplyCommentId());

        // 웹소켓메서드
        String destination = "/sub/notification/" + receiver.getEmail(); // 구독 주소 + 받을 사람 이메일
        messagingTemplate.convertAndSend(destination, sendNotification); // destination으로 sendNotification을 보냄

    }

    @Override
    public void followRefuse(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).get();
        notificationRepository.delete(notification);
    }

    @Override
    public int followAccept(Long notificationId) {

        Notification notification = notificationRepository.findById(notificationId).orElse(null);

        if (notification != null) {
            Long followerId = notification.getSender().getId(); // sender
            Long followingId = notification.getReceiver().getId(); // receiver

            Member follower = memberRepository.findById(followerId).orElseThrow(MemberNotFoundException::new);
            Member following = memberRepository.findById(followingId).orElseThrow(MemberNotFoundException::new);
            Follow follow = Follow.builder()
                                  .following(following)
                                  .follower(follower)
                                  .build();
            followRepository.save(follow);

            notificationRepository.delete(notification);

            return 1;
        }
        return 0;
    }

}
