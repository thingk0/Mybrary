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
    private Long image1;
    private Long image2;
    private int layoutType;
    private List<String> tagList;
    private List<Long> mentionList;

}
