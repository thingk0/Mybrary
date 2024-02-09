package com.mybrary.backend.domain.contents.like.repository;

import com.mybrary.backend.domain.contents.like.entity.Like;
import com.mybrary.backend.domain.contents.like.repository.custom.LikeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long>, LikeRepositoryCustom {

}
