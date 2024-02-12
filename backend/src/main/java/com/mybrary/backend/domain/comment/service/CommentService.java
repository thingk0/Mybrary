package com.mybrary.backend.domain.comment.service;

import com.mybrary.backend.domain.comment.dto.requestDto.CommentPostDto;
import com.mybrary.backend.domain.comment.dto.responseDto.CommentGetAllDto;
import com.mybrary.backend.domain.comment.dto.responseDto.CommentGetDto;

public interface CommentService {

    CommentGetDto createComment(Long myId, CommentPostDto commentPostDto);

    Long deleteComment(Long commentId);

    CommentGetAllDto getAllComment(Long myId, Long paperId);

    CommentGetAllDto getChildComments(Long commentId);


}
