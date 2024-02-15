package com.mybrary.backend.domain.contents.thread.repository.custom;

import static com.mybrary.backend.domain.contents.paper.entity.QPaper.paper;
import static com.mybrary.backend.domain.contents.paper_image.entity.QPaperImage.paperImage;
import static com.mybrary.backend.domain.contents.thread.entity.QThread.thread;
import static com.mybrary.backend.domain.follow.entity.QFollow.follow;
import static com.mybrary.backend.domain.image.entity.QImage.image;
import static com.mybrary.backend.domain.member.entity.QMember.member;
import static com.mybrary.backend.domain.mybrary.entity.QMybrary.mybrary;

import com.mybrary.backend.domain.contents.thread.dto.responseDto.GetThreadDto;
import com.mybrary.backend.domain.contents.thread.dto.responseDto.ThreadInfoGetDto;
import com.mybrary.backend.domain.contents.thread.dto.responseDto.ThreadSearchGetDto;
import com.mybrary.backend.domain.contents.thread.dto.responseDto.ThreadShareGetDto;
import com.mybrary.backend.domain.contents.thread.entity.Thread;
import com.mybrary.backend.domain.follow.entity.QFollow;
import com.mybrary.backend.domain.image.entity.QImage;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
@Log4j2
public class ThreadRepositoryImpl implements ThreadRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Optional<Integer> countMyThread(Long mybraryId) {
        return Optional.ofNullable(query.select(thread.count()
                                                      .intValue())
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

    @Override
    public Optional<List<GetThreadDto>> getFollowingThreadDtoResults(Long memberId,
                                                                     Pageable pageable) {

        return Optional.ofNullable(query.select(
                                            Projections.constructor(GetThreadDto.class, thread.id, thread.createdAt, member.id, member.name, member.nickname,
                                                                    image.url, image.id, thread.isPaperPublic, thread.isScrapEnabled))
                                        .from(thread)
                                        .leftJoin(mybrary).on(thread.mybrary.id.eq(mybrary.id))
                                        .leftJoin(member).on(mybrary.member.id.eq(member.id))
                                        .leftJoin(image).on(member.profileImage.id.eq(image.id))
                                        .leftJoin(follow).on(follow.following.id.eq(member.id).and(follow.isDeleted.eq(false)))
                                        .where((follow.follower.id.eq(memberId).or(member.id.eq(memberId))
                                                                  .and(thread.isPaperPublic.eq(true))))
                                        .groupBy(thread.id)
                                        .orderBy(thread.createdAt.desc())
                                        .offset(pageable.getOffset())
                                        .limit(pageable.getPageSize())
                                        .fetch());
    }

    /* 나와 내가 팔로잉중인 회원을 제외한 회원들의 랜덤 쓰레드 n개 조회  */
    @Override
    public Optional<List<GetThreadDto>> getRandomThreadDtoResults(Long memberId, Pageable pageable) {

        QFollow followSub = new QFollow("followSub");

        return Optional.ofNullable(query.select(
                                            Projections.constructor(GetThreadDto.class, thread.id, thread.createdAt, member.id, member.name, member.nickname,
                                                                    image.url, image.id, thread.isPaperPublic, thread.isScrapEnabled))
                                        .from(thread)
                                        .leftJoin(mybrary).on(thread.mybrary.id.eq(mybrary.id))
                                        .leftJoin(member).on(mybrary.member.id.eq(member.id))
                                        .leftJoin(image).on(member.profileImage.id.eq(image.id))
                                        .where(member.id.notIn(query.select(followSub.following.id).from(followSub)
                                                                    .where(followSub.follower.id.eq(memberId)))
                                                        .and(member.id.ne(memberId))
                                                        .and(thread.isPaperPublic.eq(true)).and(member.isProfilePublic.eq(true)))
                                        .groupBy(thread.id)
                                        .orderBy(thread.createdAt.desc())
                                        .offset(pageable.getOffset())
                                        .limit(pageable.getPageSize())
                                        .fetch());
    }

//      @Override
//      public List<GetThreadDto> getFollowingThreadDtoResults(Long memberId,
//          Pageable pageable) {
//            return query.select(
//                            Projections.constructor(GetThreadDto.class, scrap.book.id, thread.id,
//                                thread.createdAt, member.id, member.name, member.nickname,
//                                paper.isPaperPublic, paper.isScrapEnabled))
//                        .from(thread)
//                        .leftJoin(paper)
//                        .on(paper.thread.id.eq(thread.id))
//                        .leftJoin(scrap)
//                        .on(scrap.paper.id.eq(paper.id))
////                        .leftJoin(mybrary)
////                        .on(mybrary.id.eq(thread.id))
////                        .leftJoin(member)
////                        .on(member.id.eq(mybrary.id))
//                        .leftJoin(member)
//                        .on(member.id.eq(paper.member.id))
//                        .where(paper.member.id.in(
//                                        select(follow.following.member.id)
//                                            .from(follow)
//                                            .where(follow.follower.member.id.eq(memberId)))
//                                              .or(paper.member.id.eq(memberId)))
//                        .orderBy(thread.createdAt.desc())
//                        .offset(pageable.getOffset())
//                        .limit(pageable.getPageSize())
//                        .fetch();
//
//      }
//
//      /* 나와 내가 팔로잉중인 회원을 제외한 회원들의 랜덤 쓰레드 n개 조회  */
//      @Override
//      public List<GetThreadDto> getRandomThreadDtoResults(Long memberId, Pageable pageable, int count) {
//            return query.select(
//                            Projections.constructor(GetThreadDto.class, scrap.book.id, thread.id,
//                                thread.createdAt, member.id, member.name, member.nickname,
//                                paper.isPaperPublic, paper.isScrapEnabled))
//                        .from(thread)
//                        .leftJoin(paper)
//                        .on(paper.thread.id.eq(thread.id))
//                        .leftJoin(scrap)
//                        .on(scrap.paper.id.eq(paper.id))
////                        .leftJoin(mybrary)
////                        .on(mybrary.id.eq(thread.id))
////                        .leftJoin(follow)
////                        .on(follow.following.id.eq(member.id))
//                        .leftJoin(member)
//                        .on(member.id.eq(paper.member.id))
//                        .where(paper.member.id.notIn(
//                                        select(follow.following.member.id)
//                                            .from(follow)
//                                            .where(follow.follower.member.id.eq(memberId)))
//                                              .and(paper.member.id.ne(memberId)))
////            or(paper.member.id.eq(memberId)
//                        .orderBy(thread.createdAt.desc())
//                        .offset(pageable.getOffset())
//                        .limit(count)
//                        .fetch();
//      }


    /* 쓰레드 목록 조회하기에서 사용, 먼저 쓰레드부터 조회 */
    public Optional<List<Thread>> getThreadsByMemberId(Long memberId, Pageable pageable) {
        return Optional.ofNullable(query.select(thread)
                                        .from(paper)
                                        .leftJoin(thread)
                                        .on(paper.thread.id.eq(thread.id))
                                        .where(paper.member.id.eq(memberId))
                                        .orderBy(thread.createdAt.desc(), paper.createdAt.asc())
                                        .offset(pageable.getOffset())
                                        .limit(pageable.getPageSize())
                                        .fetch());
    }

    /* 이것도 쓰레드 목록 조회하기에서 사용, 쓰레드별로 페이퍼 하나에 대한 정보를 조회하도록 함 */
    @Override
    public ThreadInfoGetDto getSimpleThreadDtoResult(Long threadId) {
        return query.select(Projections.constructor(ThreadInfoGetDto.class,
                                                    paper.thread.id,
                                                    thread.createdAt,
                                                    image.id,
                                                    image.url,
                                                    paper.isPaperPublic,
                                                    paper.isScrapEnabled))
                    .from(paper)
                    .leftJoin(thread).on(paper.thread.id.eq(thread.id))
                    // 첫 번째 paper만 선택하는 조건
                    .leftJoin(paperImage).on(paper.id.eq(paperImage.paper.id).and(paperImage.imageSeq.eq(1)))
                    .leftJoin(image).on(paperImage.image.id.eq(image.id))
                    .orderBy(thread.createdAt.desc(), paper.createdAt.asc())
                    .where(thread.id.eq(threadId))
                    .limit(1)
                    .fetchOne();
    }


    /* 내 쓰레드 조회하기, 특정 회원의 쓰레드 조회하기에 공통으로 사용됨 */
//      @Override
//      public List<ThreadInfoGetDto> getSimpleThreadDtoResults(Long memberId, Pageable pageable) {
//            return query.select(Projections.constructor(ThreadInfoGetDto.class,
//                    paper.thread.id,
//                    image.url,
//                    paper.likeCount,
//                    paper.commentCount,
//                    paper.scrapCount))
//                .from(thread)
//                .leftJoin(paper)
//                .on(paper.thread.id.eq(thread.id)
//                                   .and(paper.member.id.eq(memberId)))
//                // 첫 번째 paper만 선택하는 조건
//                .leftJoin(paperImage)
//                            .on(paperImage.imageSeq.eq(1).and(paper.id.eq(paperImage.paper.id)))
//                .leftJoin(image)
//                .on(paperImage.image.id.eq(image.id))
//                .orderBy(thread.createdAt.desc(), paper.createdAt.asc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//            // 고치고있는 부분 -혜선-
////            return Optional.ofNullable(query.select(Projections.constructor(ThreadInfoGetDto.class, thread.id, image.url, paper.likeCount.count(), paper.commentCount.count(), paper.scrapCount.count()))
////                                           .from(thread)
////                                           .leftJoin(paper).on(paper.thread.id.eq(thread.id))
////                                           .leftJoin(paperImage).on(paperImage.paper.id.eq(paper.id))
////                                           .leftJoin(image).on(paperImage.image.id.eq(image.id))
////                                           .orderBy(paper.id.asc())
////                                           .orderBy(paperImage.imageSeq.asc())
////                                           .groupBy(thread.id)
////                                           .fetch());
//      }

    @Override
    public ThreadShareGetDto getThreadShare(Long threadId) {

        QImage writerImage = new QImage("writerImage");
        QImage threadImage = new QImage("threadImage");

        return query.select(Projections.constructor(ThreadShareGetDto.class, thread.id,
                                                    threadImage.id, threadImage.url, member.id, member.nickname,
                                                    writerImage.id, writerImage.url))
                    .from(thread)
                    .leftJoin(mybrary)
                    .on(thread.mybrary.id.eq(mybrary.id))
                    .leftJoin(member)
                    .on(mybrary.member.id.eq(member.id))
                    .leftJoin(writerImage)
                    .on(member.profileImage.id.eq(writerImage.id))
                    .leftJoin(paper)
                    .on(paper.thread.id.eq(thread.id))
                    .leftJoin(paperImage)
                    .on(paperImage.paper.id.eq(paper.id))
                    .leftJoin(threadImage)
                    .on(paperImage.image.id.eq(threadImage.id))
                    .orderBy(paper.id.asc())
                    .orderBy(paperImage.imageSeq.asc())
                    .limit(1)
                    .fetchOne();

    }

    @Override
    public Optional<List<ThreadSearchGetDto>> searchThreadByKeyword(Long myId, String keyword, Pageable page) {
        return Optional.empty();
    }

    @Override
    public Optional<GetThreadDto> getOneThread(Long threadId) {
        return Optional.ofNullable(query.select(
                                            Projections.constructor(GetThreadDto.class, thread.id, thread.createdAt, member.id, member.name, member.nickname,
                                                                    image.url, image.id, thread.isPaperPublic, thread.isScrapEnabled))
                                        .from(thread)
                                        .leftJoin(mybrary).on(thread.mybrary.id.eq(mybrary.id))
                                        .leftJoin(member).on(mybrary.member.id.eq(member.id))
                                        .leftJoin(image).on(member.profileImage.id.eq(image.id))
                                        .where(thread.id.eq(threadId))
                                        .fetchOne());
    }

}
