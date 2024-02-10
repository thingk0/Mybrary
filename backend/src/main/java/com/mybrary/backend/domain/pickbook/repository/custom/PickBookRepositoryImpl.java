package com.mybrary.backend.domain.pickbook.repository.custom;

import static com.mybrary.backend.domain.bookshelf.entity.QBookshelf.bookshelf;
import static com.mybrary.backend.domain.category.entity.QCategory.category;
import static com.mybrary.backend.domain.image.entity.QImage.image;
import static com.mybrary.backend.domain.member.entity.QMember.member;
import static com.mybrary.backend.domain.mybrary.entity.QMybrary.mybrary;
import static com.mybrary.backend.domain.pickbook.entity.QPickBook.pickBook;
import static com.mybrary.backend.domain.rollingpaper.entity.QRollingPaper.rollingPaper;

import com.mybrary.backend.domain.mybrary.dto.MybraryGetDto;
import com.mybrary.backend.domain.mybrary.dto.MybraryOtherGetDto;
import com.mybrary.backend.domain.mybrary.entity.Mybrary;
import com.mybrary.backend.domain.pickbook.entity.PickBook;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class PickBookRepositoryImpl implements PickBookRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public MybraryGetDto getMybrary(Long myId) {
        return query.select(
                Projections.constructor(MybraryGetDto.class, mybrary.id, mybrary.photoFrameImage,
                    mybrary.backgroundColor, mybrary.deskColor, mybrary.bookshelfColor, mybrary.easelColor,
                    member.id, member.email, member.nickname, member.intro, image.url, member.isNotifyEnabled,
                    member.isProfilePublic, bookshelf.id, rollingPaper.id
                )
            ).from(mybrary)
            .leftJoin(member).on(mybrary.member.id.eq(member.id))
            .leftJoin(bookshelf).on(bookshelf.mybrary.id.eq(mybrary.id))
            .leftJoin(rollingPaper).on(rollingPaper.mybrary.id.eq(mybrary.id))
            .leftJoin(image).on(member.profileImage.id.eq(image.id))
            .fetchFirst();
    }
    @Override
    public MybraryOtherGetDto getOtherMybrary(Long otherId) {
        return query.select(
                Projections.constructor(MybraryOtherGetDto.class, mybrary.id, mybrary.photoFrameImage,
                    mybrary.backgroundColor, mybrary.deskColor, mybrary.bookshelfColor, mybrary.easelColor,
                    member.id, member.email, member.nickname, member.intro, image.url, member.isNotifyEnabled,
                    member.isProfilePublic, bookshelf.id, rollingPaper.id
                )
            ).from(mybrary)
            .leftJoin(member).on(mybrary.member.id.eq(member.id))
            .leftJoin(bookshelf).on(bookshelf.mybrary.id.eq(mybrary.id))
            .leftJoin(rollingPaper).on(rollingPaper.mybrary.id.eq(mybrary.id))
            .leftJoin(image).on(member.profileImage.id.eq(image.id))
            .fetchFirst();
    }

    @Override
    public Optional<PickBook> getPickBook(Long bookId, Long categoryId) {
        return Optional.ofNullable(query.select(pickBook)
                                       .from(pickBook)
                                       .where(pickBook.book.id.eq(bookId).and(pickBook.category.id.eq(categoryId)))
                                       .fetchOne()
        );
    }

    @Override
    public Optional<List<PickBook>> findAllByBookId(Long bookId) {
        return Optional.ofNullable(query.select(pickBook)
                                       .from(pickBook)
                                       .where(pickBook.book.id.eq(bookId))
                                       .fetch()
        );
    }

    @Override
    public Optional<List<PickBook>> getPickBookList(Long myId, Long bookId) {
        return Optional.ofNullable(query.select(pickBook)
                                       .from(pickBook)
                                       .leftJoin(category).on(pickBook.category.id.eq(category.id))
                                       .leftJoin(bookshelf).on(category.bookshelf.id.eq(bookshelf.id))
                                       .leftJoin(mybrary).on(bookshelf.mybrary.id.eq(mybrary.id))
                                       .where(mybrary.member.id.eq(myId).and(pickBook.book.id.eq(bookId)))
                                       .fetch()
        );
    }

    @Override
    public Mybrary findByMybraryId(Long mybraryId) {
        return query.selectFrom(mybrary)
            .where(mybrary.id.eq(mybraryId))
            .fetchFirst();
    }
}
