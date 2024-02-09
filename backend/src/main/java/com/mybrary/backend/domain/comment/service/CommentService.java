package com.mybrary.backend.domain.comment.service;

import com.mybrary.backend.domain.comment.dto.responseDto.CommentGetAllDto;
import com.mybrary.backend.domain.comment.dto.requestDto.CommentPostDto;

public interface CommentService {

    Long createComment(CommentPostDto commentPostDto);

    Long deleteComment(Long commentId);

    CommentGetAllDto getAllComment(Long memberId, Long paperId);

    Long getChildComments(Long commentId);


}
