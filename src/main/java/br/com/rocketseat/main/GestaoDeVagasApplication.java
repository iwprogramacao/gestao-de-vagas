package br.com.rocketseat.main;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/* Configuração definida na classe SwaggerConfigurations
@OpenAPIDefinition(info =
    @Info(
        title = "Gestão de Vagas",
        description = "API responsável pela gestão de vagas",
        version = "1.0.0"
    )
)
@SecurityScheme(
        name = "jwt_auth",
        scheme = "bearer",
        bearerFormat = "JWT",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER
)*/
public class GestaoDeVagasApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestaoDeVagasApplication.class, args);
    }

}
