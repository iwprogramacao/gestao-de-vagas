package br.com.rocketseat.main.modules.candidate.useCases;

import br.com.rocketseat.main.exceptions.UserFoundException;
import br.com.rocketseat.main.modules.candidate.entities.CandidateEntity;
import br.com.rocketseat.main.modules.candidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service // É a camada de serviço, ou seja, possui a regra de negócio da aplicação, para então salvar no banco de dados
public class CreateCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CandidateEntity execute(CandidateEntity candidateEntity) {
        this.candidateRepository.findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
                                .ifPresent((user) -> {
                                    throw new UserFoundException("Usuário ou e-mail já cadastrado!");
                                });

        var password = passwordEncoder.encode(candidateEntity.getPassword());
        candidateEntity.setPassword(password);

        return this.candidateRepository.save(candidateEntity);
    }
}
