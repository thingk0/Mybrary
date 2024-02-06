package com.mybrary.backend.domain.contents.paper.repository.custom;

import com.mybrary.backend.domain.contents.paper.dto.GetFollowingPaperDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import static com.mybrary.backend.domain.contents.paper.entity.QPaper.paper;
import static com.mybrary.backend.domain.contents.thread.entity.QThreads.threads;

@RequiredArgsConstructor
@Repository
public class PaperRepositoryImpl implements PaperRepositoryCustom {

    private final JPAQueryFactory query;

    public List<GetFollowingPaperDto> getFollowingPaperDtoResults(Long threadsId) {
        /* 썸네일주소 1, 2를 바로 받을수 있는 방법? */
        return query.select(
                Projections.fields(GetFollowingPaperDto.class,
                    paper.id.as("id"),
                    paper.layoutType.as("layoutType"),
                    paper.content1.as("content1"),
                    paper.content2.as("content2"),
                    paper.likeCount.as("likeCount"),
                    paper.commentCount.as("commentCount"),
                    paper.scrapCount.as("scrapCount"),
                    paper.isScrapEnabled.as("isScrapEnabled")
                ))
            .from(paper)
            .leftJoin(threads).on(paper.thread.id.eq(threads.id))
            .where(threads.id.eq(threadsId))
            .groupBy(threads)
            .fetch();
    }

    @Override
    public Long deletePaperByThreadsId(Long threadsId) {
        return query
            .delete(paper)
            .where(paper.thread.id.eq(threadsId))
            .execute();
    }


}
