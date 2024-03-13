package br.com.rocketseat.main.modules.candidate.useCases;

import br.com.rocketseat.main.exceptions.JobNotFoundException;
import br.com.rocketseat.main.exceptions.UserNotFoundException;
import br.com.rocketseat.main.modules.candidate.entities.ApplyJobEntity;
import br.com.rocketseat.main.modules.candidate.entities.CandidateEntity;
import br.com.rocketseat.main.modules.candidate.repositories.ApplyJobRepository;
import br.com.rocketseat.main.modules.candidate.repositories.CandidateRepository;
import br.com.rocketseat.main.modules.company.entities.JobEntity;
import br.com.rocketseat.main.modules.company.repositories.JobRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;
    @Mock // Indica dependências da classe que deve ser testada (applyUseCase depende da candidateRepo e jobRepo)
    private CandidateRepository candidateRepository;
    @Mock
    private JobRepository jobRepository;
    @Mock
    private ApplyJobRepository applyJobRepository;

    @Test
    @DisplayName("Should not be able to apply job with candidate not found")
    public void shouldNotBeAbleToApplyJobWithCandidateNotFound() {
        try {
            applyJobCandidateUseCase.execute(null, null);
        } catch (Exception e) {
//            Assertions.assertInstanceOf(UserNotFoundException.class, e);
            assertThat(e).isInstanceOf(UserNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Should not be able to apply job with job not found")
    public void shouldNotBeAbleToApplyJobWithJobNotFound() {
        var idCandidate = UUID.randomUUID();
        var candidate = new CandidateEntity();
        candidate.setId(idCandidate);

        // Quando o execute chamar o findById, retorna esse objeto criado acima
        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));

        try {
            applyJobCandidateUseCase.execute(idCandidate, null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(JobNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Should be able to create a new ApplyJob")
    public void shouldBeAbleToCreateANewApplyJob() {
        var idCandidate = UUID.randomUUID();
        var idJob = UUID.randomUUID();

        var applyJob = ApplyJobEntity.builder()
                                     .jobId(idJob)
                                     .candidateId(idCandidate)
                                     .build();

        var applyJobCreated = ApplyJobEntity.builder()
                                            .id(UUID.randomUUID())
                                            .build();

        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(new CandidateEntity()));
        when(jobRepository.findById(idJob)).thenReturn(Optional.of(new JobEntity()));

        when(applyJobRepository.save(applyJob)).thenReturn(applyJobCreated);

        var result = applyJobCandidateUseCase.execute(idCandidate, idJob);

        assertThat(result).hasFieldOrProperty("id");
        Assertions.assertNotNull(result.getId());
    }

}

