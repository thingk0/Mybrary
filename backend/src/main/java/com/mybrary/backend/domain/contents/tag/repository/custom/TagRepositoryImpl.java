package com.mybrary.backend.domain.contents.tag.repository.custom;

import static com.mybrary.backend.domain.contents.paper.entity.QPaper.paper;
import static com.mybrary.backend.domain.contents.tag.entity.QTag.tag;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TagRepositoryImpl implements TagRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Optional<List<String>> getTagList(Long paperId) {
        return Optional.ofNullable(query.select(tag.tagName)
                                        .from(tag)
                                        .where(tag.paper.id.eq(paperId))
                                        .fetch()
        );
    }
}
