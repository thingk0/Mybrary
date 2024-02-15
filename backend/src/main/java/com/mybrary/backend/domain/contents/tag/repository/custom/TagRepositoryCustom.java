package com.mybrary.backend.domain.contents.tag.repository.custom;

import java.util.List;
import java.util.Optional;

public interface TagRepositoryCustom {

      Optional<List<String>> getTagList(Long paperId);
}
