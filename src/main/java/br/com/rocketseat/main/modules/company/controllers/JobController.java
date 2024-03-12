package br.com.rocketseat.main.modules.company.controllers;

import br.com.rocketseat.main.modules.company.DTOs.CreateJobDTO;
import br.com.rocketseat.main.modules.company.entities.JobEntity;
import br.com.rocketseat.main.modules.company.repositories.JobRepository;
import br.com.rocketseat.main.modules.company.useCases.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/company/job")
public class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("")
    @PreAuthorize("hasRole('COMPANY')")
    public JobEntity create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {
        var companyId = request.getAttribute("company_id");

        var jobEntity = JobEntity.builder()
                                 .benefits(createJobDTO.getBenefits())
                                 .description(createJobDTO.getDescription())
                                 .level(createJobDTO.getLevel())
                                 .companyId(UUID.fromString(companyId.toString()))
                                 .build();

        return this.createJobUseCase.execute(jobEntity);
    }
}
