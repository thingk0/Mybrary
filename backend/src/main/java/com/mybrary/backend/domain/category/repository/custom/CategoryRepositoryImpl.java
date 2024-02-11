package com.mybrary.backend.domain.category.repository.custom;

import static com.mybrary.backend.domain.bookshelf.entity.QBookshelf.bookshelf;
import static com.mybrary.backend.domain.category.entity.QCategory.category;
import static com.mybrary.backend.domain.member.entity.QMember.member;
import static com.mybrary.backend.domain.mybrary.entity.QMybrary.mybrary;
import static com.mybrary.backend.domain.pickbook.entity.QPickBook.pickBook;

import com.mybrary.backend.domain.category.dto.responseDto.CategoryGetDto;
import com.mybrary.backend.domain.category.dto.responseDto.MyCategoryGetDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

    private final JPAQueryFactory query;


    @Override
    public Optional<List<CategoryGetDto>> getAllCategory(Long bookshelfId) {

        return Optional.ofNullable(query.select(
                        Projections.constructor(CategoryGetDto.class, category.id, category.categoryName,
                                                category.categorySeq, pickBook.count().intValue())
                    )
                    .from(category)
                    .leftJoin(pickBook).on(pickBook.category.id.eq(category.id).and(pickBook.isDeleted.eq(false)))
                    .where(category.bookshelf.id.eq(bookshelfId))
                    .groupBy(category.id)
                    .orderBy(category.categorySeq.asc())
                    .fetch());

    }

    @Override
    public Optional<Integer> nextSeq(Long bookShelfId) {
        return Optional.ofNullable(query.select(category.categorySeq.max().intValue())
                                        .from(category)
                                        .where(category.bookshelf.id.eq(bookShelfId))
                                        .fetchOne());
    }

    @Override
    public Optional<List<MyCategoryGetDto>> getMyAllCategory(Long memberId) {
        return Optional.ofNullable(query.select(
                                            Projections.constructor(MyCategoryGetDto.class, category.id, category.categoryName)
                                        )
                                        .from(category)
                                        .where(category.bookshelf.id.eq(
                                            query.select(mybrary.id)
                                                 .from(bookshelf)
                                                 .join(mybrary).on(bookshelf.mybrary.id.eq(mybrary.id))
                                                 .where(mybrary.member.id.eq(memberId))
                                        ))
                                        .orderBy(category.categorySeq.asc())
                                        .fetch());

    }

    @Override
    public Optional<Long> findCategoryOwnerId(Long categoryId) {
        return Optional.ofNullable(query.select(member.id)
                                       .from(category)
                                       .leftJoin(bookshelf).on(category.bookshelf.id.eq(bookshelf.id))
                                       .leftJoin(mybrary).on(bookshelf.mybrary.id.eq(mybrary.id))
                                       .leftJoin(member).on(mybrary.member.id.eq(member.id))
                                       .where(category.id.eq(categoryId))
                                       .fetchOne()
        );
    }


}
