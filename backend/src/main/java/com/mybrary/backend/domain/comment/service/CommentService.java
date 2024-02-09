package com.mybrary.backend.domain.comment.service;

import com.mybrary.backend.domain.comment.dto.CommentGetAllDto;
import com.mybrary.backend.domain.comment.dto.CommentPostDto;

public interface CommentService {

    Long createComment(CommentPostDto commentPostDto);

    Long deleteComment(Long commentId);

    CommentGetAllDto getAllComment(Long memberId, Long paperId);

    Long getChildComments(Long commentId);


}
