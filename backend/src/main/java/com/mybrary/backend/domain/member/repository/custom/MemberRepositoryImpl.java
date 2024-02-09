package com.mybrary.backend.domain.member.repository.custom;

import static com.mybrary.backend.domain.image.entity.QImage.image;
import static com.mybrary.backend.domain.member.entity.QMember.member;

import com.mybrary.backend.domain.member.dto.responseDto.MemberInfoDto;
import com.mybrary.backend.domain.member.entity.Member;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

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
    public MemberInfoDto getMemberInfo(Long myId) {
        return query.select(Projections.constructor(MemberInfoDto.class, member.id, member.nickname, member.intro, image.url))
            .from(member)
            .leftJoin(image).on(member.profileImage.id.eq(image.id))
            .where(member.id.eq(myId))
            .fetchOne();

    }


}
