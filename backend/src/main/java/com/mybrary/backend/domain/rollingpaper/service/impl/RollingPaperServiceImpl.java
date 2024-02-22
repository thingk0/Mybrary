package com.mybrary.backend.domain.rollingpaper.service.impl;

import com.mybrary.backend.domain.rollingpaper.dto.RollingPaperDto;
import com.mybrary.backend.domain.rollingpaper.entity.RollingPaper;
import com.mybrary.backend.domain.rollingpaper.repository.RollingPaperRepository;
import com.mybrary.backend.domain.rollingpaper.service.RollingPaperService;
import com.mybrary.backend.global.exception.RollingPaperNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class RollingPaperServiceImpl implements RollingPaperService {

    private final RollingPaperRepository rollingPaperRepository;

    @Transactional(readOnly = true)
    @Override
    public RollingPaperDto fetch(String email, Long rollingPaperId) {
        RollingPaper rollingPaper = rollingPaperRepository.findById(rollingPaperId)
                                                          .orElseThrow(RollingPaperNotFoundException::new);
        return RollingPaperDto.from(rollingPaper);
    }

    @Transactional
    @Override
    public RollingPaperDto update(String email, RollingPaperDto rollingPaper) {
        RollingPaper beforeRollingPaper = rollingPaperRepository.findById(rollingPaper.getRollingPaperId())
                                                                .orElseThrow(RollingPaperNotFoundException::new);
        beforeRollingPaper.setRollingPaperString(rollingPaper.getRollingPaperString());
        return RollingPaperDto.from(beforeRollingPaper);
    }

}
