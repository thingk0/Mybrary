package com.mybrary.backend.domain.image.repository;

import com.mybrary.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Member, Long> {

}
