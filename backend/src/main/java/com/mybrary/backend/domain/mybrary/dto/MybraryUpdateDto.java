package com.mybrary.backend.domain.mybrary.dto;

import com.mybrary.backend.domain.image.dto.ImagePostDto;
import com.mybrary.backend.domain.member.dto.MemberGetDto;
import com.mybrary.backend.domain.rollingpaper.dto.RollingPaperGetDto;
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
public class MybraryUpdateDto {

    /**
     *  마이브러리 수정 요청
     */

    private Long mybraryId;
    private Long frameImageId;
    private int backgroundColor;
    private int deskColor;
    private int bookshelfColor;
    private int easelColor;

}
