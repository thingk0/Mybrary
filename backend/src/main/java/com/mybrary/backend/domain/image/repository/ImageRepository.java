package com.mybrary.backend.domain.image.repository;

import com.mybrary.backend.domain.image.entity.Image;
import com.mybrary.backend.domain.member.entity.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImageRepository extends JpaRepository<Image, Long> {
    /* 2개만 조회됨을 보장해야함 */
    @Query("SELECT i.url FROM Image i JOIN PaperImage pi WHERE pi.paper.id = :paperId ORDER BY pi.imageSeq ASC")
    List<String> findByPaperId(@Param("paperId") Long paperId);

}
