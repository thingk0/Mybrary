package com.mybrary.backend.domain.contents.paper_image.repository;

import com.mybrary.backend.domain.contents.paper_image.entity.PaperImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaperImageRepository extends JpaRepository<PaperImage, Long> {

}
