
package com.mybrary.backend.domain.rollingpaper.service;

import com.mybrary.backend.domain.rollingpaper.dto.RollingPaperDto;

public interface RollingPaperService {

    RollingPaperDto fetch(String email, Long rollingPaperId);

    RollingPaperDto update(String email, RollingPaperDto rollingPaper);

}
