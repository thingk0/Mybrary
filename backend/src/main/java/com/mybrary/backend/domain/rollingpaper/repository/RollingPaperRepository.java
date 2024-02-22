package com.mybrary.backend.domain.rollingpaper.repository;

import com.mybrary.backend.domain.chat.entity.ChatJoin;
import com.mybrary.backend.domain.mybrary.entity.Mybrary;
import com.mybrary.backend.domain.mybrary.repository.custom.MybraryRepositoryCustom;
import com.mybrary.backend.domain.rollingpaper.entity.RollingPaper;
import com.mybrary.backend.domain.rollingpaper.repository.custom.RollingPaperRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RollingPaperRepository extends JpaRepository<RollingPaper, Long>, RollingPaperRepositoryCustom {

}
