package com.mybrary.backend.domain.comment.service;

import com.mybrary.backend.domain.comment.dto.requestDto.CommentPostDto;
import com.mybrary.backend.domain.comment.dto.responseDto.CommentGetAllDto;

public interface CommentService {

    Long createComment(Long myId, CommentPostDto commentPostDto);

    Long deleteComment(Long commentId);

    CommentGetAllDto getAllComment(Long myId, Long paperId);

    CommentGetAllDto getChildComments(Long commentId);


}
