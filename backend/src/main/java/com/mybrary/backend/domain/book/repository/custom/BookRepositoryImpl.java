package com.mybrary.backend.domain.book.repository.custom;

import static com.mybrary.backend.domain.book.entity.QBook.book;
import static com.mybrary.backend.domain.category.entity.QCategory.category;
import static com.mybrary.backend.domain.contents.thread.entity.QThreads.threads;
import static com.mybrary.backend.domain.member.entity.QMember.member;
import static com.mybrary.backend.domain.mybrary.entity.QMybrary.mybrary;
import static com.mybrary.backend.domain.pickbook.entity.QPickBook.pickBook;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepositoryCustom {

    private final JPAQueryFactory query;


    @Override
    public Optional<Integer> countMyBook(Long bookShelfId) {

        return Optional.ofNullable(query.select(book.count().intValue())
                                       .from(book)
                                       .innerJoin(pickBook).on(pickBook.book.id.eq(book.id))
                                       .innerJoin(category).on(pickBook.category.id.eq(category.id))
                                       .where(category.bookshelf.id.eq(bookShelfId))
                                       .fetchOne()
        );

    }
}
