package com.mybrary.backend.domain.contents.thread.repository;

import com.mybrary.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadRepository extends JpaRepository<Member, Long> {

}
