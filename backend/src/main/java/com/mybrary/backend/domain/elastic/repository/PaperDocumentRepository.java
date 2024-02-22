package com.mybrary.backend.domain.elastic.repository;

import com.mybrary.backend.domain.elastic.indices.PaperDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PaperDocumentRepository extends ElasticsearchRepository<PaperDocument, Long> {

}
