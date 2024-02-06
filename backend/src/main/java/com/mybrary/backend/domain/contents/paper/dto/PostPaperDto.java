package com.mybrary.backend.domain.contents.paper.dto;

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
    private int layoutType;
    private Long bookId;
    private boolean isPaperPublic;
    private boolean isScrapEnable;
    private List<String> tagList;
}
