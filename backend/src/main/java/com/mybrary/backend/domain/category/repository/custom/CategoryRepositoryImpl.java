package com.mybrary.backend.domain.category.repository.custom;

import static com.mybrary.backend.domain.bookshelf.entity.QBookshelf.bookshelf;
import static com.mybrary.backend.domain.category.entity.QCategory.category;
import static com.mybrary.backend.domain.image.entity.QImage.image;
import static com.mybrary.backend.domain.member.entity.QMember.member;
import static com.mybrary.backend.domain.mybrary.entity.QMybrary.mybrary;
import static com.mybrary.backend.domain.pickbook.entity.QPickBook.pickBook;
import static com.mybrary.backend.domain.rollingpaper.entity.QRollingPaper.rollingPaper;

import com.mybrary.backend.domain.category.dto.CategoryGetDto;
import com.mybrary.backend.domain.mybrary.dto.MybraryGetDto;
import com.mybrary.backend.domain.mybrary.dto.MybraryOtherGetDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

    private final JPAQueryFactory query;


    @Override
    public List<CategoryGetDto> getAllCategory(Long bookshelfId) {

        return query.select(
                        Projections.constructor(CategoryGetDto.class, category.id, category.categoryName,
                                                category.categorySeq, pickBook.count().intValue())
                    )
                    .from(category)
                    .leftJoin(pickBook).on(pickBook.category.id.eq(category.id))
                    .where(category.bookshelf.id.eq(bookshelfId))
                    .groupBy(category.id)
                    .orderBy(category.categorySeq.asc())
                    .fetch();

    }

    @Override
    public Optional<Integer> nextSeq(Long bookShelfId) {
        return Optional.ofNullable(query.select(category.categorySeq.max().intValue())
                                        .from(category)
                                        .where(category.bookshelf.id.eq(bookShelfId))
                                        .fetchOne());
    }
}
