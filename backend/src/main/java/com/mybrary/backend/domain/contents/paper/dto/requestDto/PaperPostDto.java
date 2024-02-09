package com.mybrary.backend.domain.contents.paper.dto.requestDto;

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
public class PaperPostDto {

    /**
     *  스레드 저장 요청
     *  스레드 저장할 때 스레드 객체 안에 List로 같이 받을 것임
     */

    private int layoutType;
    private String content1;
    private String content2;
    private ImagePostDto image1;
    private ImagePostDto image2;
    private ImagePostDto image3;
    private ImagePostDto image4;
    private List<String> tagList;
    private List<Long> mentionIdList;

}
