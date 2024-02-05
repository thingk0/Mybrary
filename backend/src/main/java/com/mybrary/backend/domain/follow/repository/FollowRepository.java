package com.mybrary.backend.domain.follow.repository;

import com.mybrary.backend.domain.follow.entity.Follow;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.member.repository.custom.QuerydslMemberRepository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Query("select f from Follow f where f.follower.id = :myId and f.following.id = :memberId")
    Follow findFollow(@Param("myId") Long myId, @Param("memberId") Long memberId);
}
