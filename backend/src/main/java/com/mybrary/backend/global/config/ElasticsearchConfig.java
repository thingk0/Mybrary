package com.mybrary.backend.global.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;

@Configuration
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    @Value("${spring.elasticsearch.uris}")
    private String url;

    @Value("${spring.elasticsearch.username}")
    private String username;

    @Value("${spring.elasticsearch.password}")
    private String password;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                                  .connectedTo(url)
                                  .withBasicAuth(username, password)
                                  .build();
    }

    @Override
    public RestClient elasticsearchRestClient(ClientConfiguration clientConfiguration) {
        return super.elasticsearchRestClient(clientConfiguration);
    }

    @Override
    public ElasticsearchOperations elasticsearchOperations(ElasticsearchConverter elasticsearchConverter,
                                                           ElasticsearchClient elasticsearchClient) {
        return super.elasticsearchOperations(elasticsearchConverter, elasticsearchClient);
    }
}