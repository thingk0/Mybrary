package com.mybrary.backend.domain.elastic.repository;

import com.mybrary.backend.domain.elastic.indices.TagDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TagDocumentRepository extends ElasticsearchRepository<TagDocument, Long> {
}
