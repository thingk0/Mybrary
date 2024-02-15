package com.mybrary.backend.domain.elastic.repository;

import com.mybrary.backend.domain.elastic.indices.PaperDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PaperDocumentRepository extends ElasticsearchRepository<PaperDocument, Long> {

    @Query("{\"bool\": {\"should\": [" +
        "{\"match\": {\"tagList\": \"?0\"}}," +
        "{\"match\": {\"content1\": \"?0\"}}," +
        "{\"match\": {\"content2\": \"?0\"}}" +
        "]}}")
    Page<PaperDocument> findByKeyword(String keyword, Pageable pageable);

}
