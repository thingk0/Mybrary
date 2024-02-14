package com.mybrary.backend.domain.mybrary.repository.custom;

import static com.mybrary.backend.domain.bookshelf.entity.QBookshelf.bookshelf;
import static com.mybrary.backend.domain.member.entity.QMember.member;
import static com.mybrary.backend.domain.mybrary.entity.QMybrary.mybrary;
import static com.mybrary.backend.domain.rollingpaper.entity.QRollingPaper.rollingPaper;

import com.mybrary.backend.domain.image.entity.QImage;
import com.mybrary.backend.domain.mybrary.dto.MybraryGetDto;
import com.mybrary.backend.domain.mybrary.dto.MybraryOtherGetDto;
import com.mybrary.backend.domain.mybrary.entity.Mybrary;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MybraryRepositoryImpl implements MybraryRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Optional<MybraryGetDto> getMybrary(Long myId) {

        QImage profileImgae = new QImage("profileImage");
        QImage frameImgae = new QImage("frameImage");

        return Optional.ofNullable(query.select(
                                            Projections.constructor(MybraryGetDto.class, mybrary.id, frameImgae.id, frameImgae.url,
                                                                    mybrary.backgroundColor, mybrary.deskColor, mybrary.bookshelfColor, mybrary.easelColor,
                                                                    member.id, member.email, member.name, member.nickname, member.intro, profileImgae.id, profileImgae.url,
                                                                    member.isNotifyEnabled,
                                                                    member.isProfilePublic, bookshelf.id, rollingPaper.id
                                            )
                                        ).from(mybrary)
                                        .leftJoin(member).on(mybrary.member.id.eq(member.id))
                                        .leftJoin(frameImgae).on(mybrary.photoFrameImage.id.eq(frameImgae.id))
                                        .leftJoin(bookshelf).on(bookshelf.mybrary.id.eq(mybrary.id))
                                        .leftJoin(rollingPaper).on(rollingPaper.mybrary.id.eq(mybrary.id))
                                        .leftJoin(profileImgae).on(member.profileImage.id.eq(profileImgae.id))
                                        .where(member.id.eq(myId))
                                        .fetchFirst()
        );
    }

    @Override
    public Optional<MybraryOtherGetDto> getOtherMybrary(Long otherId) {
        QImage profileImgae = new QImage("profileImage");
        QImage frameImgae = new QImage("frameImage");

        return Optional.ofNullable(query.select(
                                            Projections.constructor(MybraryOtherGetDto.class, mybrary.id, frameImgae.id, frameImgae.url,
                                                                    mybrary.backgroundColor, mybrary.deskColor, mybrary.bookshelfColor, mybrary.easelColor,
                                                                    member.id, member.email, member.name, member.nickname, member.intro, profileImgae.id, profileImgae.url,
                                                                    member.isNotifyEnabled,
                                                                    member.isProfilePublic, bookshelf.id, rollingPaper.id
                                            )
                                        ).from(mybrary)
                                        .leftJoin(member).on(mybrary.member.id.eq(member.id))
                                        .leftJoin(frameImgae).on(mybrary.photoFrameImage.id.eq(frameImgae.id))
                                        .leftJoin(bookshelf).on(bookshelf.mybrary.id.eq(mybrary.id))
                                        .leftJoin(rollingPaper).on(rollingPaper.mybrary.id.eq(mybrary.id))
                                        .leftJoin(profileImgae).on(member.profileImage.id.eq(profileImgae.id))
                                        .where(member.id.eq(otherId))
                                        .fetchFirst()
        );
    }

    @Override
    public Optional<Mybrary> findMybraryByEmail(String email) {
        return Optional.ofNullable(query
                                       .select(mybrary)
                                       .from(mybrary)
                                       .innerJoin(mybrary.member, member).fetchJoin()
                                       .where(member.email.eq(email))
                                       .fetchFirst());
    }

    @Override
    public Mybrary findByMybraryId(Long mybraryId) {
        return query.selectFrom(mybrary)
                    .where(mybrary.id.eq(mybraryId))
                    .fetchFirst();
    }
}
