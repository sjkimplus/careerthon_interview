package com.sparta.careerthon_interview.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition(
        info = @Info(title = "Careerthon Project",
                description = "Careerthon 프로젝트 API 명세서",
                version = "v1")
)
@Configuration
public class SwaggerConfig {

}
