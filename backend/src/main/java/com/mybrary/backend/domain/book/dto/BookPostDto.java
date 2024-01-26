package com.mybrary.backend.domain.book.dto;

import com.mybrary.backend.domain.contents.paper.dto.PaperInBookGetDto;
import com.mybrary.backend.domain.image.dto.ImagePostDto;
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
public class BookPostDto {

    /**
     *  책 생성
     *
     */

    private String title;
    private String titleFont;
    private ImagePostDto coverImage;
    private int coverLayout;
    private int coverColorCode;
    private Long categoryId;

}
