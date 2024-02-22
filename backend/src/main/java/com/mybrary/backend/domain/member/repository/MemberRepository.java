package com.mybrary.backend.domain.member.repository;

import com.mybrary.backend.domain.follow.entity.Follow;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.member.repository.custom.MemberRepositoryCustom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

//    @Query("select f from Follow f where f.following.id = :memberId and f.follower.id = :myId and f.isDeleted = false ")
//    Follow isFollowed(@Param("myId") Long myId, @Param("memberId") Long memberId);
}
