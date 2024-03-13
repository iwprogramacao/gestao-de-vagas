package br.com.rocketseat.main.modules.candidate.useCases;

import br.com.rocketseat.main.exceptions.JobNotFoundException;
import br.com.rocketseat.main.exceptions.UserNotFoundException;
import br.com.rocketseat.main.modules.candidate.entities.ApplyJobEntity;
import br.com.rocketseat.main.modules.candidate.repositories.ApplyJobRepository;
import br.com.rocketseat.main.modules.candidate.repositories.CandidateRepository;
import br.com.rocketseat.main.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplyJobCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private ApplyJobRepository applyJobRepository;

    public ApplyJobEntity execute(UUID idCandidate, UUID idJob) {
        this.candidateRepository.findById(idCandidate)
                                .orElseThrow(UserNotFoundException::new);
        this.jobRepository.findById(idJob)
                          .orElseThrow(JobNotFoundException::new);

        var applyJob = ApplyJobEntity.builder().candidateId(idCandidate).jobId(idJob).build();
        return this.applyJobRepository.save(applyJob);
    }
}
