package br.com.rocketseat.main;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info =
    @Info(
        title = "Gestão de Vagas",
        description = "API responsável pela gestão de vagas",
        version = "1.0.0"
    )
)
public class GestaoDeVagasApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestaoDeVagasApplication.class, args);
    }

}
