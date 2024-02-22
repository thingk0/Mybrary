package com.mybrary.backend.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(title = "MyBrary API 명세서",
                 description = "MyBrary 서비스 API 명세서",
                 version = "v1"))
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .addServersItem(new Server().url("/"))
            .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
            .components(new Components()
                            .addSecuritySchemes("BearerAuth", new SecurityScheme()
                                .name("Authorization")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("Bearer")
                                .bearerFormat("JWT")));
    }

    // swagger 그룹 추가
    // 전체보기
    @Bean
    public GroupedOpenApi all() {
        return GroupedOpenApi.builder()
                             .group("a. 전체")
                             .pathsToMatch("/api/v1/**")
                             .build();
    }

    @Bean
    public GroupedOpenApi memberGroup() {
        return GroupedOpenApi.builder()
                             .group("b. 회원")
                             .pathsToMatch("/api/v1/member/**")
                             .build();
    }

    @Bean
    public GroupedOpenApi bookGroup() {
        return GroupedOpenApi.builder()
                             .group("c. 책")
                             .pathsToMatch("/api/v1/book/**")
                             .build();
    }

    @Bean
    public GroupedOpenApi categoryGroup() {
        return GroupedOpenApi.builder()
                             .group("d. 카테고리")
                             .pathsToMatch("/api/v1/category/**")
                             .build();
    }

    @Bean
    public GroupedOpenApi chatGroup() {
        return GroupedOpenApi.builder()
                             .group("e. 채팅")
                             .pathsToMatch("/api/v1/chat/**")
                             .build();
    }

    @Bean
    public GroupedOpenApi commentGroup() {
        return GroupedOpenApi.builder()
                             .group("f. 댓글")
                             .pathsToMatch("/api/v1/comment/**")
                             .build();
    }

    @Bean
    public GroupedOpenApi paperGroup() {
        return GroupedOpenApi.builder()
                             .group("g. 페이퍼")
                             .pathsToMatch("/api/v1/paper/**")
                             .build();
    }

    @Bean
    public GroupedOpenApi threadGroup() {
        return GroupedOpenApi.builder()
                             .group("h. 스레드")
                             .pathsToMatch("/api/v1/thread/**")
                             .build();
    }

    @Bean
    public GroupedOpenApi imageGroup() {
        return GroupedOpenApi.builder()
                             .group("i. 이미지")
                             .pathsToMatch("/api/v1/image/**")
                             .build();
    }

    @Bean
    public GroupedOpenApi mybraryGroup() {
        return GroupedOpenApi.builder()
                             .group("j. 마이브러리")
                             .pathsToMatch("/api/v1/mybrary/**")
                             .build();
    }

    @Bean
    public GroupedOpenApi notificationGroup() {
        return GroupedOpenApi.builder()
                             .group("k. 알림")
                             .pathsToMatch("/api/v1/notification/**")
                             .build();
    }

    @Bean
    public GroupedOpenApi searchGroup() {
        return GroupedOpenApi.builder()
                             .group("l. 검색")
                             .pathsToMatch("/api/v1/search/**")
                             .build();
    }

    @Bean
    public GroupedOpenApi rollingPaperGroup() {
        return GroupedOpenApi.builder()
                             .group("m. 롤링페이퍼")
                             .pathsToMatch("/api/v1/rollingpaper/**")
                             .build();
    }

}
