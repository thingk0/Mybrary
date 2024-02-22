package com.mybrary.backend.domain.contents.paper.dto.requestDto;

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
public class PostPaperDto {

    private String content1;
    private String content2;
    private Long imageId1;
    private Long imageId2;
    private int layoutType;
    private List<String> tagList;
    private List<Long> mentionList;

}
