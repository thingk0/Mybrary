
package com.mybrary.backend.domain.rollingpaper.service;
import com.mybrary.backend.domain.mybrary.dto.MybraryGetDto;
import com.mybrary.backend.domain.mybrary.dto.MybraryOtherGetDto;
import com.mybrary.backend.domain.mybrary.dto.MybraryUpdateDto;
import com.mybrary.backend.domain.rollingpaper.dto.RollingPaperGetDto;
import com.mybrary.backend.domain.rollingpaper.dto.RollingPaperPostDto;

public interface RollingPaperService {

    RollingPaperGetDto getRollingPaper(String email, Long rollingPaperId);

    Long saveRollingPaper(String email, RollingPaperPostDto rollingPaper);
}
