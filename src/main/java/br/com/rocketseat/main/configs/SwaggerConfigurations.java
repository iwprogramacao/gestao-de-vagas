package br.com.rocketseat.main.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfigurations {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title("Gestão de Vagas")
                                            .description("API responsável pela gestão de vagas")
                                            .version("1.0.0"))
//                            .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication")) - Adiciona camada de segurança para todas as rotas
//                            .components(new Components().addSecuritySchemes("Bearer Authentication", createSecurityScheme()));
                            .schemaRequirement("jwt_auth", createSecurityScheme());
    }

    private SecurityScheme createSecurityScheme() {
        return new SecurityScheme().name("jwt_auth")
                                   .type(SecurityScheme.Type.HTTP)
                                   .scheme("bearer")
                                   .bearerFormat("JWT");
    }
}
