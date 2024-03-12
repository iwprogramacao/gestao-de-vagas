package br.com.rocketseat.main.modules.candidate.useCases;

import br.com.rocketseat.main.modules.candidate.DTOs.AuthCandidateRequestDTO;
import br.com.rocketseat.main.modules.candidate.DTOs.AuthCandidateResponseDTO;
import br.com.rocketseat.main.modules.candidate.repositories.CandidateRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${security.jwt.secret.candidate}") // Busca das variáveis de ambiente o valor configurado
    private String jwtSecret;


    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException {
        var candidate = candidateRepository.findByUsername(authCandidateRequestDTO.username())
                                           .orElseThrow(() -> {
                                               throw new UsernameNotFoundException("Usuário e/ou senha incorreto(s).");
                                           });

        var passwordMatches = passwordEncoder.matches(authCandidateRequestDTO.password(), candidate.getPassword());

        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

        var expiresIn = Instant.now()
                               .plus(Duration.ofHours(2));

        var token = JWT.create()
                       .withIssuer("W.A.S.D")
                       .withExpiresAt(expiresIn)
                       .withClaim("roles", Arrays.asList("CANDIDATE"))
                       .withSubject(candidate.getId()
                                             .toString())
                       .sign(algorithm);

        var authCandidateResponse = AuthCandidateResponseDTO.builder()
                                                            .acess_token(token)
                                                            .expires_in(expiresIn.toEpochMilli())
                                                            .build();

        return authCandidateResponse;
    }

}
