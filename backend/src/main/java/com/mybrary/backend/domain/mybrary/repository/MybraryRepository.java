package com.mybrary.backend.domain.mybrary.repository;

import com.mybrary.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MybraryRepository extends JpaRepository<Member, Long> {

}
