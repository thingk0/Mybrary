package com.mybrary.backend.domain.contents.tag.repository;

import com.mybrary.backend.domain.contents.tag.entity.Tag;
import com.mybrary.backend.domain.contents.tag.repository.custom.TagRepositoryCustom;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long>, TagRepositoryCustom {

    Optional<List<Tag>> getTagsByPaperId(Long paperId);

    Long deleteAllByPaperId(Long paperId);

}
