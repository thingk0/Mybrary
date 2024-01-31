package com.mybrary.backend.domain.member.repository;

import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.member.repository.custom.QuerydslMemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslMemberRepository {
}
