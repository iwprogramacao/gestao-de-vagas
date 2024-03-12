package br.com.rocketseat.main.modules.company.controllers;

import br.com.rocketseat.main.exceptions.UserFoundException;
import br.com.rocketseat.main.modules.company.entities.CompanyEntity;
import br.com.rocketseat.main.modules.company.useCases.CreateCompanyUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Informa que esta camada serve para os controles da API
@RequestMapping("/company")
public class CompanyController {

    @Autowired // Faz a injeção de dependência e cuida do ciclo de vida do componente
    private CreateCompanyUseCase createCompanyUseCase;

    @PostMapping("")
    public ResponseEntity<Object> create(@Valid @RequestBody CompanyEntity companyEntity) {
        try {
            var result = this.createCompanyUseCase.execute(companyEntity);

            return ResponseEntity.ok()
                                 .body(result);
        } catch (UserFoundException userFoundException) {
            return ResponseEntity.badRequest()
                                 .body(userFoundException.getMessage());
        }
    }
}
