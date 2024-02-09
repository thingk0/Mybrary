package com.mybrary.backend.domain.comment.dto.responseDto;

import java.util.List;
import lombok.Builder;

@Builder
public class CommentGetAllDto {

    private Long paperId;
    private List<CommentGetDto> commentGetDtoList;

}
