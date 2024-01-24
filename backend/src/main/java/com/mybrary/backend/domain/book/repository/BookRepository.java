package com.mybrary.backend.domain.book.repository;

import com.mybrary.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Member, Long> {

}
