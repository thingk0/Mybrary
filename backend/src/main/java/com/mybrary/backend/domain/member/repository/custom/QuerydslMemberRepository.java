package com.mybrary.backend.domain.member.repository.custom;

import com.mybrary.backend.domain.member.entity.Member;
import java.util.Optional;

public interface QuerydslMemberRepository {
    Optional<Member> findByEmail(String email);
}
