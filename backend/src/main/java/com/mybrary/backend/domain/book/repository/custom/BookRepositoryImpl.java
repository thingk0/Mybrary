package com.mybrary.backend.domain.book.repository.custom;

import static com.mybrary.backend.domain.book.entity.QBook.book;
import static com.mybrary.backend.domain.bookmarker.entity.QBookMarker.bookMarker;
import static com.mybrary.backend.domain.bookshelf.entity.QBookshelf.bookshelf;
import static com.mybrary.backend.domain.category.entity.QCategory.category;
import static com.mybrary.backend.domain.contents.paper.entity.QPaper.paper;
import static com.mybrary.backend.domain.contents.scrap.entity.QScrap.scrap;
import static com.mybrary.backend.domain.follow.entity.QFollow.follow;
import static com.mybrary.backend.domain.image.entity.QImage.image;
import static com.mybrary.backend.domain.member.entity.QMember.member;
import static com.mybrary.backend.domain.mybrary.entity.QMybrary.mybrary;
import static com.mybrary.backend.domain.pickbook.entity.QPickBook.pickBook;

import com.mybrary.backend.domain.book.dto.responseDto.BookForMainThreadDto;
import com.mybrary.backend.domain.book.dto.responseDto.BookGetDto;
import com.mybrary.backend.domain.book.dto.responseDto.BookListGetFromPaperDto;
import com.mybrary.backend.domain.book.dto.responseDto.MyBookGetDto;
import com.mybrary.backend.domain.image.entity.QImage;
import com.mybrary.backend.domain.member.dto.responseDto.MemberInfoDto;
import com.mybrary.backend.domain.member.entity.Member;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

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
                                                                                            image2.id, image2.url),
                                                                    book.coverTitle, image1.id, image1.url, book.coverLayout,
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
//            query.select(Projections.constructor(MyBookGetDto.class, book.id, book.coverTitle, scrap.count()))
//                 .from(book)
//                 .leftJoin(scrap).on(scrap.book.id.eq(book.id))
//                 .leftJoin(pickBook).on(pickBook.book.id.eq(book.id))
//                 .where(book.member.id.eq(memberId).and(pickBook.category.id.eq(categoryId)))
//                 .groupBy(book.id)
//                 .fetch());
            query.select(Projections.constructor(MyBookGetDto.class, pickBook.book.id, pickBook.book.coverTitle, scrap.count()))
                 .from(book)
                 .leftJoin(pickBook).on(pickBook.book.id.eq(book.id).and(pickBook.book.member.id.eq(memberId)))
                 .leftJoin(scrap).on(book.id.eq(scrap.book.id))
                 .where(pickBook.category.id.eq(categoryId).and(pickBook.isDeleted.eq(false)))
                 .groupBy(book.id)
                 .fetch());
    }

    @Override
    public Optional<List<BookListGetFromPaperDto>> getBookListFromPaper(Long paperId) {

        QImage profileImage = new QImage("profileImage");
        QImage coverImage = new QImage("coverImage");

        return Optional.ofNullable(query.select(Projections.constructor(BookListGetFromPaperDto.class,
                                                                        book.id, member.id, member.nickname,
                                                                        profileImage.id, profileImage.url, book.coverTitle,
                                                                        coverImage.id, coverImage.url, book.coverLayout,
                                                                        book.coverColor))
                                        .from(paper)
                                        .leftJoin(scrap).on(scrap.paper.id.eq(paper.id))
                                        .leftJoin(book).on(scrap.book.id.eq(book.id))
                                        .leftJoin(member).on(paper.member.id.eq(member.id))
                                        .leftJoin(profileImage).on(member.profileImage.id.eq(profileImage.id))
                                        .leftJoin(coverImage).on(book.coverImage.id.eq(coverImage.id))
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
                                        .where(paper.member.id.eq(writerId).and(paper.id.eq(paperId))
                                                              .and(book.member.id.eq(writerId)))
                                        .fetch());
    }

    @Override
    public Optional<List<BookGetDto>> searchBookByKeyword(Long myId, String keyword, Pageable page) {

        QImage profileImage = new QImage("profileImage");
        QImage bookCoverImage = new QImage("bookCoverImage");

        return Optional.ofNullable(query.select(Projections.constructor(BookGetDto.class, book.id,
                                                                        Projections.constructor(MemberInfoDto.class, member.id,
                                                                                                member.nickname, member.intro,
                                                                                                profileImage.id,
                                                                                                profileImage.url),
                                                                        book.coverTitle, bookCoverImage.id, bookCoverImage.url,
                                                                        book.coverLayout, book.coverColor))
                                        .from(book)
                                        .leftJoin(member).on(book.member.id.eq(member.id))
                                        .leftJoin(profileImage)
                                        .on((member.profileImage.id.eq(profileImage.id)))
                                        .leftJoin(bookCoverImage).on(book.coverImage.id.eq(bookCoverImage.id))
                                        .where(book.coverTitle.like('%' + keyword + '%')
                                                              .and(member.isProfilePublic.eq(true)
                                                                                         .or(member.isProfilePublic.eq(
                                                                                             false).and(
                                                                                             member.id.in(
                                                                                                 query.select(
                                                                                                          follow.following.id)
                                                                                                      .from(
                                                                                                          follow)
                                                                                                      .where(
                                                                                                          follow.follower.id.eq(
                                                                                                              myId)))))))
                                        .offset(page.getOffset())
                                        .limit(page.getPageSize())
                                        .fetch());
    }

    @Override
    public Optional<Member> findMember(Long bookId) {
        return Optional.ofNullable(query.select(member)
                                        .from(book)
                                        .leftJoin(member).on(book.member.id.eq(member.id)).fetchJoin()
                                        .where(book.id.eq(bookId))
                                        .fetchOne());
    }

    @Override
    public Optional<Member> findMemberByCategoryId(Long categoryId) {
        return Optional.ofNullable(query.select(member)
                                        .from(category)
                                        .leftJoin(bookshelf).on(category.bookshelf.id.eq(bookshelf.id)).fetchJoin()
                                        .leftJoin(mybrary).on(bookshelf.mybrary.id.eq(mybrary.id)).fetchJoin()
                                        .leftJoin(member).on(mybrary.member.id.eq(member.id)).fetchJoin()
                                        .where(category.id.eq(categoryId))
                                        .fetchOne());
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