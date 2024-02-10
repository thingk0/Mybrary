package com.mybrary.backend.domain.book.repository.custom;

import static com.mybrary.backend.domain.book.entity.QBook.book;
import static com.mybrary.backend.domain.bookmarker.entity.QBookMarker.bookMarker;
import static com.mybrary.backend.domain.category.entity.QCategory.category;
import static com.mybrary.backend.domain.contents.paper.entity.QPaper.paper;
import static com.mybrary.backend.domain.contents.scrap.entity.QScrap.scrap;
import static com.mybrary.backend.domain.image.entity.QImage.image;
import static com.mybrary.backend.domain.member.entity.QMember.member;
import static com.mybrary.backend.domain.pickbook.entity.QPickBook.pickBook;

import com.mybrary.backend.domain.book.dto.responseDto.BookForMainThreadDto;
import com.mybrary.backend.domain.book.dto.responseDto.BookGetDto;
import com.mybrary.backend.domain.book.dto.responseDto.BookListGetFromPaperDto;
import com.mybrary.backend.domain.book.dto.responseDto.MyBookGetDto;
import com.mybrary.backend.domain.image.entity.QImage;
import com.mybrary.backend.domain.member.dto.responseDto.MemberInfoDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Optional<Integer> countMyBook(Long bookShelfId) {
        return Optional.ofNullable(query.select(book.count().intValue())
                                        .from(book)
                                        .leftJoin(pickBook)
                                        .on(pickBook.book.id.eq(book.id))
                                        .leftJoin(category)
                                        .on(pickBook.category.id.eq(category.id))
                                        .where(category.bookshelf.id.eq(bookShelfId))
                                        .fetchOne()
        );
    }

    @Override
    public Optional<List<BookGetDto>> getAllBookByCategoryId(Long categoryId) {
        QImage image1 = new QImage("image1");
        QImage image2 = new QImage("image2");
        return Optional.ofNullable(query.select(
                                            Projections.constructor(BookGetDto.class, book.id,
                                                                    Projections.constructor(MemberInfoDto.class, member.id, member.nickname, member.intro,
                                                                                            image2.url),
                                                                    book.coverTitle, image1.url, book.coverLayout,
                                                                    book.coverColor, bookMarker.bookMarkerIndex))
                                        .from(book)
                                        .leftJoin(image1)
                                        .on(book.coverImage.id.eq(image1.id))
                                        .leftJoin(bookMarker)
                                        .on(bookMarker.book.id.eq(book.id))
                                        .leftJoin(pickBook)
                                        .on(pickBook.book.id.eq(book.id))
                                        .leftJoin(member)
                                        .on(book.member.id.eq(member.id))
                                        .leftJoin(image2)
                                        .on(member.profileImage.id.eq(image2.id))
                                        .where(pickBook.category.id.eq(categoryId))
                                        .fetch());
    }

    @Override
    public Optional<List<MyBookGetDto>> getAllMyBookList(Long memberId, Long categoryId) {
        return Optional.ofNullable(
            query.select(Projections.constructor(MyBookGetDto.class, book.id, book.coverTitle, scrap.count()))
                 .from(book)
                 .leftJoin(scrap).on(scrap.book.id.eq(book.id))
                 .leftJoin(pickBook).on(pickBook.book.id.eq(book.id))
                 .where(book.member.id.eq(memberId).and(pickBook.category.id.eq(categoryId)))
                 .groupBy(book.id)
                 .fetch());
    }

    @Override
    public Optional<List<BookListGetFromPaperDto>> getBookListFromPaper(Long paperId) {
        return Optional.ofNullable(query.select(Projections.constructor(BookListGetFromPaperDto.class,
                                                                        book.id, book.coverTitle, member.id, member.nickname,
                                                                        image.url))
                                        .from(paper)
                                        .leftJoin(scrap).on(scrap.paper.id.eq(paper.id))
                                        .leftJoin(book).on(scrap.book.id.eq(book.id))
                                        .leftJoin(member).on(paper.member.id.eq(member.id))
                                        .leftJoin(image).on(member.profileImage.id.eq(image.id))
                                        .where(paper.id.eq(paperId).and(paper.member.id.eq(book.member.id)))
                                        .fetch()
        );
    }

    @Override
    public Optional<List<BookForMainThreadDto>> getBookForMainThread(Long writerId, Long paperId) {
        return Optional.ofNullable(query.select(Projections.constructor(BookForMainThreadDto.class, book.id, book.coverTitle))
                                       .from(book)
                                       .leftJoin(scrap).on(scrap.book.id.eq(book.id))
                                       .leftJoin(paper).on(scrap.paper.id.eq(paper.id))
                                       .where(paper.member.id.eq(writerId).and(paper.id.eq(paperId)).and(book.member.id.eq(writerId)))
                                       .fetch());
    }

//    @Override
//    public Optional<List<MyBookGetDto>> getAllMyBookList(Long memberId, Long categoryId) {
//        return Optional.ofNullable(query.select(Projections.constructor(MyBookGetDto.class, book.id, book.coverTitle, scrap.count()))
//                                        .from(book)
//                                        .innerJoin(scrap).on(scrap.book.id.eq(book.id))
//                                        .where(book.member.id.eq(memberId))
//                                        .groupBy(book.id)
//                                        .fetch());
//    }

}