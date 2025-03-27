package com.exemplo.taskmanager.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .addSecurityItem(new SecurityRequirement().addList("Auth Basic"))
        .components(new Components()
            .addSecuritySchemes("Auth Basic",
                new SecurityScheme()
                    .name("Auth Basic")
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("Basic")
                    .bearerFormat("Basic")));
  }
}