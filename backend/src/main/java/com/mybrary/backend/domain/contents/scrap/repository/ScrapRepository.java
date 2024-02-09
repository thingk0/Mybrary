package com.mybrary.backend.domain.contents.scrap.repository;

import com.mybrary.backend.domain.contents.scrap.entity.Scrap;
import com.mybrary.backend.domain.contents.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {

}
