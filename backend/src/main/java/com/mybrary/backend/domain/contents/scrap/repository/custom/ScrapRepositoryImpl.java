package com.mybrary.backend.domain.contents.scrap.repository.custom;

import static com.mybrary.backend.domain.contents.scrap.entity.QScrap.scrap;

import com.mybrary.backend.domain.contents.scrap.entity.Scrap;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ScrapRepositoryImpl implements ScrapRepositoryCustom {

      private final JPAQueryFactory query;

      @Override
      public Optional<Integer> getScrapCount(Long paperId) {
            return Optional.ofNullable(query.select(scrap.count().intValue())
                                            .from(scrap)
                                            .where(scrap.paper.id.eq(paperId))
                                            .fetchOne()
            );
      }

      @Override
      public Optional<Scrap> getScrap(Long bookId, Long paperId) {
            return Optional.ofNullable(query.select(scrap)
                                           .from(scrap)
                                           .where(scrap.book.id.eq(bookId).and(scrap.paper.id.eq(paperId)))
                                           .fetchOne()
            );
      }

      @Override
      public Optional<Integer> findLastPaperSeq(Long bookId) {
            return Optional.ofNullable(query.select(scrap.paperSeq.max())
                                           .from(scrap)
                                           .where(scrap.book.id.eq(bookId)).fetchOne());
      }



}
