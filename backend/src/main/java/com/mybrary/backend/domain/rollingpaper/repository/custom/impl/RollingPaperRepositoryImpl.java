package com.mybrary.backend.domain.rollingpaper.repository.custom.impl;

import static com.mybrary.backend.domain.bookshelf.entity.QBookshelf.bookshelf;
import static com.mybrary.backend.domain.member.entity.QMember.member;
import static com.mybrary.backend.domain.mybrary.entity.QMybrary.mybrary;
import static com.mybrary.backend.domain.rollingpaper.entity.QRollingPaper.rollingPaper;

import com.mybrary.backend.domain.image.entity.QImage;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.mybrary.dto.MybraryGetDto;
import com.mybrary.backend.domain.mybrary.dto.MybraryOtherGetDto;
import com.mybrary.backend.domain.mybrary.entity.Mybrary;
import com.mybrary.backend.domain.rollingpaper.repository.custom.RollingPaperRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RollingPaperRepositoryImpl implements RollingPaperRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Optional<Member> findOwner(Long rollingPaperId) {
        return Optional.ofNullable(query.select(member)
                                       .from(rollingPaper)
                                       .leftJoin(mybrary).on(rollingPaper.mybrary.id.eq(mybrary.id))
                                       .leftJoin(member).on(mybrary.member.id.eq(member.id))
                                       .where(rollingPaper.id.eq(rollingPaperId))
                                       .fetchOne());
    }
}
