package br.com.rocketseat.main.modules.company.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateJobDTO {

    @Schema(example = "Vaga para pessoa desenvolvedora Java", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;
    @Schema(example = "Gympass, plano de saúde", requiredMode = Schema.RequiredMode.REQUIRED)
    private String benefits;
    @Schema(example = "Júnior", requiredMode = Schema.RequiredMode.REQUIRED)
    private String level;
}

