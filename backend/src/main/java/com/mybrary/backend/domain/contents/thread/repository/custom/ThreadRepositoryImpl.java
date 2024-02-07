package com.mybrary.backend.domain.contents.thread.repository.custom;

import static com.mybrary.backend.domain.contents.paper.entity.QPaper.paper;
import static com.mybrary.backend.domain.contents.paper_image.entity.QPaperImage.paperImage;
import static com.mybrary.backend.domain.contents.scrap.entity.QScrap.scrap;
import static com.mybrary.backend.domain.contents.thread.entity.QThread.thread;
import static com.mybrary.backend.domain.follow.entity.QFollow.follow;
import static com.mybrary.backend.domain.image.entity.QImage.image;
import static com.mybrary.backend.domain.member.entity.QMember.member;
import static com.mybrary.backend.domain.mybrary.entity.QMybrary.mybrary;
import static com.querydsl.jpa.JPAExpressions.select;

import com.mybrary.backend.domain.contents.thread.dto.GetThreadDto;
import com.mybrary.backend.domain.contents.thread.dto.ThreadInfoGetDto;
import com.mybrary.backend.domain.contents.thread.dto.ThreadShareGetDto;
import com.mybrary.backend.domain.contents.thread.entity.Thread;
import com.mybrary.backend.domain.image.entity.QImage;
import com.mybrary.backend.domain.member.dto.MemberInfoDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ThreadRepositoryImpl implements ThreadRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Optional<Integer> countMyThread(Long mybraryId) {
        return Optional.ofNullable(query.select(thread.count().intValue())
            .from(thread)
            .where(thread.mybrary.id.eq(mybraryId))
            .fetchOne());

    }

    @Override
    public Optional<Thread> findByThreadId(Long threadId) {
        return Optional.ofNullable(query.selectFrom(thread)
                                        .where(thread.id.eq(threadId))
                                        .fetchFirst());
    }

    public List<GetThreadDto> getFollowingThreadDtoResults(Long memberId,
                                                           Pageable pageable) {
        return query.select(
                        Projections.constructor(GetThreadDto.class, scrap.book.id, thread.id,
                                                thread.createdAt, member.id, member.name, member.nickname,
                                                member.profileImage.url))
                    .from(thread)
                    .leftJoin(paper).on(paper.thread.id.eq(thread.id))
                    .leftJoin(scrap).on(scrap.paper.id.eq(paper.id))
                    .leftJoin(mybrary).on(mybrary.id.eq(thread.id))
                    .leftJoin(member).on(member.id.eq(mybrary.id))
                    .leftJoin(follow).on(follow.following.id.eq(member.id))
                    .where(follow.follower.id.eq(memberId).or(member.id.eq(memberId)))
                    .groupBy(thread.id)
                    .orderBy(thread.createdAt.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

    }

    /* 나와 내가 팔로잉중인 회원을 제외한 회원들의 랜덤 쓰레드 n개 조회  */
    @Override
    public List<GetThreadDto> getRandomThreadDtoResults(Long memberId, Pageable pageable) {
        return query.select(
                        Projections.constructor(GetThreadDto.class, scrap.book.id, thread.id,
                                                thread.createdAt, member.id, member.name, member.nickname,
                                                member.profileImage.url))
                    .from(thread)
                    .leftJoin(paper).on(paper.thread.id.eq(thread.id))
                    .leftJoin(scrap).on(scrap.paper.id.eq(paper.id))
                    .leftJoin(mybrary).on(mybrary.id.eq(thread.id))
                    .leftJoin(member).on(member.id.eq(mybrary.id))
                    .leftJoin(follow).on(follow.following.id.eq(member.id))
                    .where(paper.member.id.ne(
                                    select(follow.following.member.id)
                                        .from(follow)
                                        .where(follow.follower.member.id.eq(memberId)))
                                          .and(paper.member.id.ne(memberId)))
                    .groupBy(thread.id)
                    .orderBy(thread.createdAt.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
    }

    /* 내 쓰레드 조회하기, 특정 회원의 쓰레드 조회하기에 공통으로 사용됨 */
    @Override
    public List<ThreadInfoGetDto> getSimpleThreadDtoResults(Long memberId, Pageable pageable) {
        return query.select(
                        Projections.constructor(ThreadInfoGetDto.class, thread.id,
                                                image.url, paper.likeCount, paper.commentCount, paper.scrapCount))
                    .from(thread)
                    .leftJoin(paper).on(paper.thread.id.eq(thread.id).and(paper.member.id.eq(memberId)))
                    .leftJoin(paperImage).on(paper.id.eq(paperImage.paper.id).and(paperImage.imageSeq.eq(1)))
                    .leftJoin(image).on(paperImage.paper.id.eq(paper.id))
                    .groupBy(thread.id)
                    .orderBy(thread.createdAt.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
    }

    @Override
    public ThreadShareGetDto getThreadShare(Long threadId) {

        QImage writerImage = new QImage("writerImage");
        QImage threadImage = new QImage("threadImage");

        return query.select(Projections.constructor(ThreadShareGetDto.class, thread.id,
                                                    threadImage.url, member.id, member.nickname,
                                                    writerImage.url))
                    .from(thread)
                    .leftJoin(mybrary).on(thread.mybrary.id.eq(mybrary.id))
                    .leftJoin(member).on(mybrary.member.id.eq(member.id))
                    .leftJoin(writerImage).on(member.profileImage.id.eq(writerImage.id))
                    .leftJoin(paper).on(paper.thread.id.eq(thread.id))
                    .leftJoin(paperImage).on(paperImage.paper.id.eq(paper.id))
                    .leftJoin(threadImage).on(paperImage.image.id.eq(threadImage.id))
                    .orderBy(paper.id.asc()).orderBy(paperImage.imageSeq.asc())
                    .limit(1)
                    .fetchOne();

    }

}
