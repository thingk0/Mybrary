package com.mybrary.backend.domain.comment.repository.custom;

import com.mybrary.backend.domain.comment.dto.CommentGetDto;
import java.util.List;
import java.util.Optional;

public interface CommentRepositoryCustom {

    Optional<List<CommentGetDto>> getCommentGetDtoListByPaperId(Long paperId);
}
