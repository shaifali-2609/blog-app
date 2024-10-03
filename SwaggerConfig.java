package blogapp_api.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                    .title("Backend Course")
                    .description("LearnCode API Documentation")
                    .version("1.0")
                    .termsOfService("https://www.learncode.com/terms")
                    .contact(new Contact()
                        .name("Shaifali")
                        .url("http://localhost:6565")
                        .email("learncode@example.com")
                    )
                    .license(new io.swagger.v3.oas.models.info.License()
                        .name("License of API")
                        .url("https://www.learncode.com/license")
                    )
                )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                    .addSecuritySchemes("bearerAuth",
                        new SecurityScheme()
                            .name("bearerAuth")
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                    )
                );
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-api")
                .pathsToMatch("/**")
                .build();
    }
}
