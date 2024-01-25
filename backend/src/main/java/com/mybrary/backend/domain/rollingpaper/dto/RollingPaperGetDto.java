package com.mybrary.backend.domain.rollingpaper.dto;

import com.mybrary.backend.domain.image.dto.ImagePostDto;
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
public class RollingPaperGetDto {

    private Long rollingPaperId;
    private ImagePostDto image;

}
