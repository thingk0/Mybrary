package com.mybrary.backend.domain.contents.tag.repository.custom;

import static com.mybrary.backend.domain.contents.tag.entity.QTag.tag;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TagRepositoryImpl implements TagRepositoryCustom {

      private final JPAQueryFactory query;

      public Long deleteAllByPaperId(Long paperId) {
            return query.delete(tag)
                        .where(tag.paper.id.eq(paperId))
                        .execute();
      }
}
