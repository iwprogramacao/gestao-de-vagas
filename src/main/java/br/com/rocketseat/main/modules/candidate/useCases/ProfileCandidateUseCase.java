package br.com.rocketseat.main.modules.candidate.useCases;

import br.com.rocketseat.main.exceptions.UserFoundException;
import br.com.rocketseat.main.modules.candidate.DTOs.ProfileCandidateResponseDTO;
import br.com.rocketseat.main.modules.candidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute(UUID candidateId) {
        var candidate = candidateRepository.findById(candidateId)
                                           .orElseThrow(() -> {
                                               throw new UserFoundException("Usuário não encontrado.");
                                           });

        var profileCandidade = ProfileCandidateResponseDTO.builder()
                                                          .id(candidate.getId())
                                                          .name(candidate.getName())
                                                          .email(candidate.getEmail())
                                                          .description(candidate.getDescription())
                                                          .username(candidate.getUsername())
                                                          .build();
        return profileCandidade;
    }
}
