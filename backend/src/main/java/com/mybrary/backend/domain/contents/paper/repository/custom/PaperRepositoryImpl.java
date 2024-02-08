package com.mybrary.backend.domain.contents.paper.repository.custom;

import static com.mybrary.backend.domain.contents.paper.entity.QPaper.paper;
import static com.mybrary.backend.domain.contents.thread.entity.QThread.thread;

import com.mybrary.backend.domain.contents.paper.dto.GetFollowingPaperDto;
import com.mybrary.backend.domain.contents.paper.dto.PaperGetDto;
import com.mybrary.backend.domain.member.dto.MemberGetDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import static com.mybrary.backend.domain.contents.paper.entity.QPaper.paper;
import static com.mybrary.backend.domain.contents.thread.entity.QThread.thread;
import static com.mybrary.backend.domain.image.entity.QImage.image;

@RequiredArgsConstructor
@Repository
public class PaperRepositoryImpl implements PaperRepositoryCustom {

    private final JPAQueryFactory query;

    public List<GetFollowingPaperDto> getFollowingPaperDtoResults(Long threadId) {
        return query.select(
                        Projections.fields(GetFollowingPaperDto.class,
                                           paper.id.as("id"),
                                           paper.layoutType.as("layoutType"),
                                           paper.content1.as("content1"),
                                           paper.content2.as("content2"),
                                           paper.likeCount.as("likeCount"),
                                           paper.commentCount.as("commentCount"),
                                           paper.scrapCount.as("scrapCount"),
                                           paper.isScrapEnabled.as("isScrapEnabled"),
                                           paper.isPaperPublic.as("isPaperPublic")
                        ))
                    .from(paper)
                    .leftJoin(thread).on(paper.thread.id.eq(thread.id))
                    .where(thread.id.eq(threadId))
                    .groupBy(thread)
                    .fetch();
    }

    @Override
    public Long deletePaperByThreadsId(Long threadId) {
        return query
            .delete(paper)
            .where(paper.thread.id.eq(threadId))
            .execute();
    }

//    private Long paperId;
//    private String createdAt;
//    private int layoutType;
//    private String content1;
//    private String content2;
//    private String image1Url;
//    private String image2Url;
//    private int likeCount;
//    private int commentCount;
//    private int scrapCount;
//    private boolean isLiked;
//    boolean isScrapEnabled;
//    boolean isPaperPublic;
    @Override
    public List<PaperGetDto> getPaperGetDto(Long threadId) {
        return query.select(
                        Projections.fields(PaperGetDto.class,
                                           paper.id.as("id"),
                                           paper.createdAt.as("createdAt"),
                                           paper.layoutType.as("layoutType"),
                                           paper.content1.as("content1"),
                                           paper.content2.as("content2"),
                                           image.url.as("image1Url"),
                                           image.url.as("image2Url"),
                                           paper.likeCount.as("likeCount"),
                                           paper.commentCount.as("commentCount"),
                                           paper.scrapCount.as("scrapCount"),
                                           paper.isScrapEnabled.as("isScrapEnabled"),
                                           paper.isPaperPublic.as("isPaperPublic")
                        ))
                    .from(paper)
                    .leftJoin(image).on(paper.thread.id.eq(thread.id))
                    .where(thread.id.eq(threadId))
                    .groupBy(thread)
                    .fetch();

    }


}
