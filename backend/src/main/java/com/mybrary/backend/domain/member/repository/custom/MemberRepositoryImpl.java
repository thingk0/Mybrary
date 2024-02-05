package com.mybrary.backend.domain.member.repository.custom;

import static com.mybrary.backend.domain.member.entity.QMember.member;

import com.mybrary.backend.domain.member.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Optional<Member> findByEmail(String email) {
        return Optional.ofNullable(query.selectFrom(member)
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


}
