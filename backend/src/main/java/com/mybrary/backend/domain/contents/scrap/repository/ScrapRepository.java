package com.mybrary.backend.domain.contents.scrap.repository;

import com.mybrary.backend.domain.contents.scrap.entity.Scrap;
import com.mybrary.backend.domain.contents.scrap.repository.custom.ScrapRepositoryCustom;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapRepository extends JpaRepository<Scrap, Long>, ScrapRepositoryCustom {

      Optional<Scrap> findByPaperId(Long paperId);
}
