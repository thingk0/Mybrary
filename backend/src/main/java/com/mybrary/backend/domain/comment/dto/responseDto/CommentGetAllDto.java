package com.mybrary.backend.domain.comment.dto.responseDto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentGetAllDto {

    private Long paperId;
    private List<CommentGetDto> commentGetDtoList;

}
