package com.mybrary.backend.domain.elastic.repository;

import com.mybrary.backend.domain.elastic.indices.MemberDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MemberDocumentRepository extends ElasticsearchRepository<MemberDocument, Long> {

}
