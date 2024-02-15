package com.mybrary.backend.domain.elastic.repository;

import com.mybrary.backend.domain.elastic.indices.SearchTermDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SearchTermDocumentRepository extends ElasticsearchRepository<SearchTermDocument, String> {

}
