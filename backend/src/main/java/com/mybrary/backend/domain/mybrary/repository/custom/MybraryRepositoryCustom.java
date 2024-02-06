package com.mybrary.backend.domain.mybrary.repository.custom;

import com.mybrary.backend.domain.mybrary.entity.Mybrary;

public interface MybraryRepositoryCustom {
    Mybrary findByMybraryId(Long mybraryId);
}
