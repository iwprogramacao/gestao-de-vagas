package br.com.rocketseat.main.modules.company.useCases;

import br.com.rocketseat.main.exceptions.CompanyNotFoundException;
import br.com.rocketseat.main.modules.company.entities.JobEntity;
import br.com.rocketseat.main.modules.company.repositories.CompanyRepository;
import br.com.rocketseat.main.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateJobUseCase {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private CompanyRepository companyRepository;
    public JobEntity execute(JobEntity jobEntity) {
        companyRepository.findById(jobEntity.getCompanyId()).orElseThrow(CompanyNotFoundException::new);
        return this.jobRepository.save(jobEntity);
    }
}

