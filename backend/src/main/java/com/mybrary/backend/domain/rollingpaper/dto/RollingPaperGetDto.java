package com.mybrary.backend.domain.rollingpaper.dto;

import com.mybrary.backend.domain.image.dto.ImagePostDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RollingPaperGetDto {

    private Long rollingPaperId;
    private String rollingPaperString;

}
