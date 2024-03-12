package br.com.rocketseat.main.modules.company.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthCompanyResponseDTO {

    private String acess_token;
    private Long expires_in;
}
