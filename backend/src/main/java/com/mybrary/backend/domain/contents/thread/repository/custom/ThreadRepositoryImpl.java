package com.mybrary.backend.domain.contents.thread.repository.custom;

import static com.mybrary.backend.domain.bookshelf.entity.QBookshelf.bookshelf;
import static com.mybrary.backend.domain.contents.thread.entity.QThreads.threads;
import static com.mybrary.backend.domain.image.entity.QImage.image;
import static com.mybrary.backend.domain.member.entity.QMember.member;
import static com.mybrary.backend.domain.mybrary.entity.QMybrary.mybrary;
import static com.mybrary.backend.domain.rollingpaper.entity.QRollingPaper.rollingPaper;

import com.mybrary.backend.domain.mybrary.dto.MybraryGetDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ThreadRepositoryImpl implements ThreadRepositoryCustom {

    private final JPAQueryFactory query;


    @Override
    public Optional<Integer> countMyThread(Long mybraryId) {
        return Optional.ofNullable(query.select(threads.count().intValue())
                                        .from(threads)
                                        .where(threads.mybrary.id.eq(mybraryId))
                                        .fetchOne());

    }
}
