package com.assignment.Astrotalk.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AstroTalk")
                        .version("1.0.0")
                        .description("API documentation for AstroTalk Spring Boot application")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                        .contact(new Contact().name("Ankit Prasad").email("ankit.prasad@coditas.com")))
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("BearerAuth", new SecurityScheme()
                                .name("Authorization")
                                .type(Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }

    @Bean
    public OperationCustomizer customOperationCustomizer() {
        return (operation, handlerMethod) -> {
            // Exclude /api/v1/login from the security requirement
            if (operation.getSummary() != null && !operation.getSummary().isEmpty()) {
                if ("Login API".equals(operation.getSummary())) {
                    return operation; // Skip applying security for /api/v1/login
                }
            }

            // Apply security to all other operations
            operation.addSecurityItem(new SecurityRequirement().addList("BearerAuth"));
            return operation;
        };
    }
}