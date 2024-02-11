package com.mybrary.backend.domain.contents.paper.repository.custom;

import static com.mybrary.backend.domain.book.entity.QBook.book;
import static com.mybrary.backend.domain.contents.paper.entity.QPaper.paper;
import static com.mybrary.backend.domain.contents.paper_image.entity.QPaperImage.paperImage;
import static com.mybrary.backend.domain.contents.scrap.entity.QScrap.scrap;
import static com.mybrary.backend.domain.contents.thread.entity.QThread.thread;
import static com.mybrary.backend.domain.image.entity.QImage.image;
import static com.mybrary.backend.domain.member.entity.QMember.member;

import com.mybrary.backend.domain.contents.paper.dto.responseDto.GetFollowingPaperDto;
import com.mybrary.backend.domain.contents.paper.dto.responseDto.PaperGetDto;
import com.mybrary.backend.domain.contents.paper.dto.responseDto.PaperInBookGetDto;
import com.mybrary.backend.domain.member.dto.responseDto.MemberInfoDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PaperRepositoryImpl implements PaperRepositoryCustom {

    private final JPAQueryFactory query;

    public Optional<List<GetFollowingPaperDto>> getFollowingPaperDtoResults(Long threadId) {
//        return query.select(
//                        Projections.fields(GetFollowingPaperDto.class,
//                                           paper.id.as("id"),
//                                           paper.layoutType.as("layoutType"),
//                                           paper.content1.as("content1"),
//                                           paper.content2.as("content2"),
//                                           paper.likeCount.as("likeCount"),
//                                           paper.commentCount.as("commentCount"),
//                                           paper.scrapCount.as("scrapCount")
//                        ))
//                    .from(paper)
//                    .leftJoin(thread).on(paper.thread.id.eq(threadId))
//                    .fetch();

        return Optional.ofNullable(query.select(
                                            Projections.constructor(GetFollowingPaperDto.class, paper.id, paper.layoutType,
                                                                    paper.content1, paper.content2, paper.likeCount.as("likeCount"),
                                                                    paper.commentCount.as("commentCount"),
                                                                    paper.scrapCount.as("scrapCount"), paper.mentionList, paper.isPaperPublic,
                                                                    paper.isScrapEnabled))
                                        .from(paper)
                                        .where(paper.thread.id.eq(threadId))
                                        .fetch());
    }

    @Override
    public Long deletePaperByThreadId(Long threadId) {
        return query
            .delete(paper)
            .where(paper.thread.id.eq(threadId))
            .execute();
    }

    @Override
    public Optional<List<PaperInBookGetDto>> getPaperList(Long bookId) {

        return Optional.ofNullable(query.select(
                                            Projections.constructor(PaperInBookGetDto.class, paper.id, paper.createdAt, paper.layoutType, paper.content1,
                                                                    paper.content2))
                                        .from(paper)
                                        .leftJoin(scrap).on(scrap.paper.id.eq(paper.id))
                                        .leftJoin(book).on(scrap.book.id.eq(book.id))
                                        .where(book.id.eq(bookId))
                                        .orderBy(scrap.paperSeq.asc())
                                        .fetch()
        );
    }

    @Override
    public Optional<MemberInfoDto> getWriter(Long paperId) {
        return Optional.ofNullable(
            query.select(Projections.constructor(MemberInfoDto.class, member.id, member.nickname, member.intro, image.id, image.url))
                 .from(paper)
                 .leftJoin(member).on(paper.member.id.eq(member.id))
                 .leftJoin(image).on(member.profileImage.id.eq(image.id))
                 .where(paper.id.eq(paperId))
                 .fetchOne()
        );
    }

    @Override
    public Optional<Long> getImageUrl(Long paperId, int seq) {
        return Optional.ofNullable(query.select(paperImage.image.id)
                                        .from(paperImage)
                                        .where(paperImage.paper.id.eq(paperId).and(paperImage.imageSeq.eq(seq)))
                                        .fetchOne()
        );
    }

    @Override
    public Optional<Long> getThreadIdByPaperId(Long paperId) {
        return Optional.ofNullable(query.select(paper.thread.id)
                                        .from(paper)
                                        .where(paper.id.eq(paperId))
                                        .fetchOne()
        );
    }

    @Override
    public Optional<List<PaperGetDto>> getPaperGetDto(Long threadId) {
        return Optional.ofNullable(query.select(
                                            Projections.fields(PaperGetDto.class,
                                                               paper.id.as("paperId"),
                                                               paper.createdAt.as("createdAt"),
                                                               paper.layoutType.as("layoutType"),
                                                               paper.content1.as("content1"),
                                                               paper.content2.as("content2"),
                                                               paper.mentionList.as("mentionListString")
                                            ))
                                        .from(paper)
//                                        .leftJoin(paperImage).on(paperImage.paper.id.eq(paper.id).and(thread.id.eq(threadId)))
                                        .leftJoin(thread).on(paper.thread.id.eq(thread.id))
                                        .where(thread.id.eq(threadId))
                                        .fetch());
    }


}
