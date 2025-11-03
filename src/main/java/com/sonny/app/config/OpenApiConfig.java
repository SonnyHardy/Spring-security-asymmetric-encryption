package com.sonny.app.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Spring security JWT asymmetric Encryption",
                        email = "sonnydev265@gmail.com",
                        url = "https://example.com"
                ),
                description = "OpenAPI documentation for Spring Security project",
                title = "OpenAPI specification - Spring Security JWT asymmetric Encryption",
                version = "1.0",
                license = @License(
                        name = "License name",
                        url = "https://example.com/license"
                ),
                termsOfService = "https://example.com/terms"
        ),
        servers = {
                @Server(
                        url = "https://spring-security-asymmetric-encryption-488816087046.europe-west1.run.app",
                        description = "Production environment"
                ),
                @Server(
                        url = "http://localhost:8080",
                        description = "Local environment"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT Bearer token authentication",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
