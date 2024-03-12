package br.com.rocketseat.main.modules.company.DTOs;

import lombok.Data;

@Data
public class CreateJobDTO {

    private String description;
    private String benefits;
    private String level;
}

