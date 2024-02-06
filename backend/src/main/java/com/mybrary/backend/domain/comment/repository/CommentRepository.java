package com.mybrary.backend.domain.comment.repository;

import com.mybrary.backend.domain.comment.entity.Comment;
import com.mybrary.backend.domain.comment.repository.custom.CommentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {

}
