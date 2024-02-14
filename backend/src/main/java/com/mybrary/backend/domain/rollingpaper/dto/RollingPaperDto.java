package com.mybrary.backend.domain.rollingpaper.dto;

import com.mybrary.backend.domain.rollingpaper.entity.RollingPaper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RollingPaperDto {

    private Long rollingPaperId;
    private String rollingPaperString;

    public static RollingPaperDto from(RollingPaper rollingPaper) {
        return RollingPaperDto.builder()
                              .rollingPaperId(rollingPaper.getId())
                              .rollingPaperString(rollingPaper.getRollingPaperString())
                              .build();
    }

}
