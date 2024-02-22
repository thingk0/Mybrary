package com.mybrary.backend.domain.contents.tag.service;

import java.util.List;

public interface TagService {
    List<String> getTagNameList(Long paperId);
}
