package com.mybrary.backend.global.config;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

@Configuration
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    private final String url;
    private final String username;
    private final String password;

    public ElasticsearchConfig(@Value("${spring.elasticsearch.uris}") String url,
                               @Value("${spring.elasticsearch.username}") String username,
                               @Value("${spring.elasticsearch.password}") String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                                  .connectedTo(url)
                                  .withBasicAuth(username, password)
                                  .withConnectTimeout(Duration.ofSeconds(5))
                                  .withSocketTimeout(Duration.ofSeconds(3))
                                  .build();
    }
}
