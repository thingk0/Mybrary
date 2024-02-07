package com.mybrary.backend.domain.book.repository.custom;

import static com.mybrary.backend.domain.book.entity.QBook.book;
import static com.mybrary.backend.domain.bookmarker.entity.QBookMarker.bookMarker;
import static com.mybrary.backend.domain.category.entity.QCategory.category;
import static com.mybrary.backend.domain.contents.scrap.entity.QScrap.scrap;
import static com.mybrary.backend.domain.member.entity.QMember.member;
import static com.mybrary.backend.domain.pickbook.entity.QPickBook.pickBook;

import com.mybrary.backend.domain.book.dto.BookGetDto;
import com.mybrary.backend.domain.book.dto.MyBookGetDto;
import com.mybrary.backend.domain.image.entity.QImage;
import com.mybrary.backend.domain.member.dto.MemberInfoDto;
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
                                            .innerJoin(pickBook)
                                            .on(pickBook.book.id.eq(book.id))
                                            .innerJoin(category)
                                            .on(pickBook.category.id.eq(category.id))
                                            .where(category.bookshelf.id.eq(bookShelfId))
                                            .fetchOne()
            );

      }

      @Override
      public List<BookGetDto> getAllBookByCategoryId(Long categoryId) {

            QImage image1 = new QImage("image1");
            QImage image2 = new QImage("image2");

            return query.select(
                            Projections.constructor(BookGetDto.class, book.id,
                                Projections.constructor(MemberInfoDto.class, member.id, member.nickname, member.intro, image2.url),
                                book.coverTitle, image1.url, book.coverLayout,
                                book.coverColor, bookMarker.bookMarkerIndex))
                        .from(book)
                        .innerJoin(image1)
                        .on(book.coverImage.id.eq(image1.id))
                        .leftJoin(bookMarker)
                        .on(bookMarker.book.id.eq(book.id))
                        .innerJoin(pickBook)
                        .on(pickBook.book.id.eq(book.id))
                        .innerJoin(member)
                        .on(book.member.id.eq(member.id))
                        .innerJoin(image2)
                        .on(member.profileImage.id.eq(image2.id))
                        .where(pickBook.category.id.eq(categoryId))
                        .fetch();

      }

      @Override
      public Optional<List<MyBookGetDto>> getAllMyBookList(Long memberId, Long categoryId) {
            return Optional.ofNullable(query.select(Projections.constructor(MyBookGetDto.class, book.id, book.coverTitle, scrap.count()))
                                            .from(book)
                                            .innerJoin(scrap).on(scrap.book.id.eq(book.id))
                                            .where(book.member.id.eq(memberId))
                                            .groupBy(book.id)
                                            .fetch());
      }


}
