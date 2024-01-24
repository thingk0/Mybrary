package com.mybrary.backend.domain.category.repository;

import com.mybrary.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Member, Long> {

}
