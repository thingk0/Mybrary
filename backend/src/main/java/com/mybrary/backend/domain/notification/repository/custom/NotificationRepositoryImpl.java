package com.mybrary.backend.domain.notification.repository.custom;

import static com.mybrary.backend.domain.bookshelf.entity.QBookshelf.bookshelf;
import static com.mybrary.backend.domain.image.entity.QImage.image;
import static com.mybrary.backend.domain.member.entity.QMember.member;
import static com.mybrary.backend.domain.mybrary.entity.QMybrary.mybrary;
import static com.mybrary.backend.domain.notification.entity.QNotification.notification;
import static com.mybrary.backend.domain.rollingpaper.entity.QRollingPaper.rollingPaper;

import com.mybrary.backend.domain.mybrary.dto.MybraryGetDto;
import com.mybrary.backend.domain.mybrary.dto.MybraryOtherGetDto;
import com.mybrary.backend.domain.mybrary.entity.Mybrary;
import com.mybrary.backend.domain.notification.entity.Notification;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Notification getForFollowCancel(Long myId, Long memberId) {
        return query.select(notification)
            .from(notification)
            .where(notification.sender.id.eq(myId).and(notification.receiver.id.eq(memberId)).and(notification.notifyType.eq(1)))
            .fetchOne();
    }

}
