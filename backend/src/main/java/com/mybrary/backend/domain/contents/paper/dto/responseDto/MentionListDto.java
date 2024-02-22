package com.mybrary.backend.domain.contents.paper.dto.responseDto;

import com.mybrary.backend.domain.book.dto.responseDto.BookForMainThreadDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentionListDto {

    private Long id;
    private String nickname;
}
