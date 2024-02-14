package com.mybrary.backend.domain.member.repository.custom;

import static com.mybrary.backend.domain.bookshelf.entity.QBookshelf.bookshelf;
import static com.mybrary.backend.domain.category.entity.QCategory.category;
import static com.mybrary.backend.domain.follow.entity.QFollow.follow;
import static com.mybrary.backend.domain.image.entity.QImage.image;
import static com.mybrary.backend.domain.member.entity.QMember.member;
import static com.mybrary.backend.domain.mybrary.entity.QMybrary.mybrary;
import static com.mybrary.backend.domain.notification.entity.QNotification.notification;

import com.mybrary.backend.domain.follow.entity.Follow;
import com.mybrary.backend.domain.member.dto.responseDto.FollowerDto;
import com.mybrary.backend.domain.member.dto.responseDto.FollowingDto;
import com.mybrary.backend.domain.member.dto.responseDto.MemberGetDto;
import com.mybrary.backend.domain.member.dto.responseDto.MemberInfoDto;
import com.mybrary.backend.domain.member.dto.responseDto.MyFollowerDto;
import com.mybrary.backend.domain.member.dto.responseDto.MyFollowingDto;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.notification.entity.Notification;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Optional<Member> searchByEmail(String email) {
        return Optional.ofNullable(query.selectFrom(member)
                                        .leftJoin(image).on(member.profileImage.id.eq(image.id)).fetchJoin()
                                        .where(member.email.eq(email))
                                        .fetchFirst());
    }

    @Override
    public boolean isNicknameDuplicate(String nickname) {
        return query
            .selectFrom(member)
            .where(member.nickname.eq(nickname))
            .fetchCount() > 0;
    }

    @Override
    public Optional<MemberInfoDto> getMemberInfo(Long myId) {
        return Optional.ofNullable(query.select(
                                            Projections.constructor(MemberInfoDto.class, member.id, member.nickname, member.intro, image.id, image.url))
                                        .from(member)
                                        .leftJoin(image).on(member.profileImage.id.eq(image.id))
                                        .where(member.id.eq(myId))
                                        .fetchOne());

    }

    @Override
    public Optional<List<MyFollowingDto>> getAllMyFollowing(Long myId) {
        return Optional.ofNullable(query.select(
                                            Projections.constructor(MyFollowingDto.class, member.id, member.name, member.nickname, image.id, image.url))
                                        .from(follow)
                                        .leftJoin(member).on(follow.following.id.eq(member.id))
                                        .leftJoin(image).on(member.profileImage.id.eq(image.id))
                                        .where(follow.follower.id.eq(myId))
                                        .fetch());
    }

    @Override
    public Optional<List<MyFollowerDto>> getAllMyFollower(Long myId) {
        return Optional.ofNullable(query.select(
                                            Projections.constructor(MyFollowerDto.class, member.id, member.name, member.nickname, image.id, image.url))
                                        .from(follow)
                                        .leftJoin(member).on(follow.follower.id.eq(member.id))
                                        .leftJoin(image).on(member.profileImage.id.eq(image.id))
                                        .where(follow.following.id.eq(myId))
                                        .fetch());
    }

    @Override
    public Optional<List<FollowingDto>> getAllFollowing(Long memberId) {
        return Optional.ofNullable(query.select(
                                            Projections.constructor(FollowingDto.class, member.id, member.name, member.nickname, image.id, image.url))
                                        .from(follow)
                                        .leftJoin(member).on(follow.following.id.eq(member.id))
                                        .leftJoin(image).on(member.profileImage.id.eq(image.id))
                                        .where(follow.follower.id.eq(memberId))
                                        .fetch());
    }

    @Override
    public Optional<List<FollowerDto>> getAllFollower(Long memberId) {
        return Optional.ofNullable(
            query.select(Projections.constructor(FollowerDto.class, member.id, member.name, member.nickname, image.id, image.url))
                 .from(follow)
                 .leftJoin(member).on(follow.follower.id.eq(member.id))
                 .leftJoin(image).on(member.profileImage.id.eq(image.id))
                 .where(follow.following.id.eq(memberId))
                 .fetch());
    }

    @Override
    public Optional<Follow> isFollowed(Long myId, Long memberId) {
        return Optional.ofNullable(query.select(follow)
                                        .from(follow)
                                        .where(follow.following.id.eq(memberId).and(follow.follower.id.eq(myId)))
                                        .fetchOne());
    }

    @Override
    public Optional<Notification> isRequested(Long myId, Long memberId) {
        return Optional.ofNullable(query.select(notification)
                                        .from(notification)
                                        .where(notification.sender.id.eq(myId).and(
                                            notification.receiver.id.eq(memberId).and(notification.notifyType.eq(1))))
                                        .fetchOne());
    }

    @Override
    public Optional<Member> findByCategoryId(Long categoryId) {
        return Optional.ofNullable(query.select(member)
                                        .from(category)
                                        .leftJoin(bookshelf).on(category.bookshelf.id.eq(bookshelf.id))
                                        .leftJoin(mybrary).on(bookshelf.mybrary.id.eq(mybrary.id))
                                        .leftJoin(member).on(mybrary.member.id.eq(member.id))
                                        .where(category.id.eq(categoryId))
                                        .fetchOne());
    }

    @Override
    public Optional<List<MemberGetDto>> searchAcoountByKo(Long myId, String keyword, Pageable page) {
        return Optional.ofNullable(query.select(
                                            Projections.constructor(MemberGetDto.class, member.id, member.email, member.name, member.nickname, member.intro,
                                                                    image.id, image.url, member.isProfilePublic, member.isNotifyEnabled))
                                        .from(member)
                                        .leftJoin(image).on(member.profileImage.id.eq(image.id))
                                        .where(member.name.like('%' + keyword + '%'))
//                                        .where(member.name.like('%' + keyword + '%').and(member.isProfilePublic.eq(true)
//                                                                                                               .or(member.isProfilePublic.eq(
//                                                                                                                   false).and(
//                                                                                                                   member.id.in(
//                                                                                                                       query.select(
//                                                                                                                                follow.following.id)
//                                                                                                                            .from(
//                                                                                                                                follow)
//                                                                                                                            .where(
//                                                                                                                                follow.follower.id.eq(
//                                                                                                                                    myId)))))))
                                        .offset(page.getOffset())
                                        .limit(page.getPageSize())
                                        .fetch());
    }

    @Override
    public Optional<List<MemberGetDto>> searchAcoountByEn(Long myId, String keyword, Pageable page) {
        return Optional.ofNullable(query.select(
                                            Projections.constructor(MemberGetDto.class, member.id, member.email, member.name, member.nickname, member.intro,
                                                                    image.id, image.url, member.isProfilePublic, member.isNotifyEnabled))
                                        .from(member)
                                        .leftJoin(image).on(member.profileImage.id.eq(image.id))
                                        .where(member.nickname.like('%' + keyword + '%'))
//                                        .where(member.nickname.like('%' + keyword + '%').and(member.isProfilePublic.eq(true)
//                                                                                                                   .or(member.isProfilePublic.eq(
//                                                                                                                       false).and(
//                                                                                                                       member.id.in(
//                                                                                                                           query.select(
//                                                                                                                                    follow.following.id)
//                                                                                                                                .from(
//                                                                                                                                    follow)
//                                                                                                                                .where(
//                                                                                                                                    follow.follower.id.eq(
//                                                                                                                                        myId)))))))
                                        .offset(page.getOffset())
                                        .limit(page.getPageSize())
                                        .fetch());
    }


}
