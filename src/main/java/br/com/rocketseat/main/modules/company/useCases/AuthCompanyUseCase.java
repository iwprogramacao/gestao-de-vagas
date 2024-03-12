package br.com.rocketseat.main.modules.company.useCases;

import br.com.rocketseat.main.modules.company.DTOs.AuthCompanyDTO;
import br.com.rocketseat.main.modules.company.DTOs.AuthCompanyResponseDTO;
import br.com.rocketseat.main.modules.company.repositories.CompanyRepository;
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
public class AuthCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${security.jwt.secret}") // Busca das variáveis de ambiente o valor configurado
    private String jwtSecret;

    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        var company = companyRepository.findByUsername(authCompanyDTO.getUsername())
                                       .orElseThrow(() -> {
                                           throw new UsernameNotFoundException("Usuário e/ou senha incorreto(s).");
                                       });

        // Verificar a senha é igual
        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        // Gerador do algorítmo do JWT
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret); // Secret do JWT
        var expires_in = Instant.now() // Adiciona expiração do token
                                .plus(Duration.ofDays(2));
        var token = JWT.create()
                       .withIssuer("W.A.S.D") // Quem está emitindo (geralmente o nome da empresa)
                       .withSubject(company.getId() // Id do usuário
                                           .toString())
                       .withExpiresAt(expires_in)
                       .withClaim("roles", Arrays.asList("COMPANY"))
                       .sign(algorithm); // Algoritmo de criação

        var authCompanyResponseDTO = AuthCompanyResponseDTO.builder()
                                                           .acess_token(token)
                                                           .expires_in(expires_in.toEpochMilli())
                                                           .build();

        return authCompanyResponseDTO;
    }
}
