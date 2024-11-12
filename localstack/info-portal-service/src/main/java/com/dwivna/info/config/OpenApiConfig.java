package com.dwivna.info.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Information Service API",
                description = "API for providing application information",
                version = "v1.0"
        )
)
public class OpenApiConfig {
}
