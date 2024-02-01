package com.mybrary.backend.domain.notification.service.impl;

import com.mybrary.backend.domain.member.dto.MemberInfoDto;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.member.repository.MemberRepository;
import com.mybrary.backend.domain.notification.dto.NotificationGetDto;
import com.mybrary.backend.domain.notification.dto.NotificationPostDto;
import com.mybrary.backend.domain.notification.entity.Notification;
import com.mybrary.backend.domain.notification.repository.NotificationRepository;
import com.mybrary.backend.domain.notification.service.NotificationService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;

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
                                                     member.getIntro(), null);

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

    }
}
