package com.mybrary.backend.domain.mybrary.repository.custom;

import static com.mybrary.backend.domain.bookshelf.entity.QBookshelf.bookshelf;
import static com.mybrary.backend.domain.contents.thread.entity.QThreads.threads;
import static com.mybrary.backend.domain.follow.entity.QFollow.follow;
import static com.mybrary.backend.domain.image.entity.QImage.image;
import static com.mybrary.backend.domain.member.entity.QMember.member;
import static com.mybrary.backend.domain.mybrary.entity.QMybrary.mybrary;
import static com.mybrary.backend.domain.rollingpaper.entity.QRollingPaper.rollingPaper;

import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.mybrary.dto.MybraryGetDto;
import com.mybrary.backend.domain.mybrary.dto.MybraryOtherGetDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MybraryRepositoryImpl implements MybraryRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public MybraryGetDto getMybrary(Long myId) {
        return query.select(
                        Projections.constructor(MybraryGetDto.class, mybrary.id, mybrary.photoFrameImage,
                                                mybrary.backgroundColor, mybrary.deskColor, mybrary.bookshelfColor, mybrary.easelColor,
                                                member.id, member.email, member.nickname, member.intro, image.url, member.isNotifyEnabled,
                                                member.isProfilePublic, bookshelf.id, rollingPaper.id
                        )

                    ).from(mybrary)
                    .innerJoin(member).on(mybrary.member.id.eq(member.id))
                    .innerJoin(bookshelf).on(bookshelf.mybrary.id.eq(mybrary.id))
                    .innerJoin(rollingPaper).on(rollingPaper.mybrary.id.eq(mybrary.id))
                    .innerJoin(image).on(member.profileImage.id.eq(image.id))
                    .fetchFirst();

    }

    @Override
    public MybraryOtherGetDto getOtherMybrary(Long otherId) {
        return query.select(
                        Projections.constructor(MybraryOtherGetDto.class, mybrary.id, mybrary.photoFrameImage,
                                                mybrary.backgroundColor, mybrary.deskColor, mybrary.bookshelfColor, mybrary.easelColor,
                                                member.id, member.email, member.nickname, member.intro, image.url, member.isNotifyEnabled,
                                                member.isProfilePublic, bookshelf.id, rollingPaper.id
                        )

                    ).from(mybrary)
                    .innerJoin(member).on(mybrary.member.id.eq(member.id))
                    .innerJoin(bookshelf).on(bookshelf.mybrary.id.eq(mybrary.id))
                    .innerJoin(rollingPaper).on(rollingPaper.mybrary.id.eq(mybrary.id))
                    .innerJoin(image).on(member.profileImage.id.eq(image.id))
                    .fetchFirst();
    }
}
