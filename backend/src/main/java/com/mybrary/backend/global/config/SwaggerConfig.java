package com.mybrary.backend.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(title = "MyBrary API 명세서",
                 description = "MyBrary 서비스 API 명세서",
                 version = "v1"))
@Configuration
public class SwaggerConfig {


}
