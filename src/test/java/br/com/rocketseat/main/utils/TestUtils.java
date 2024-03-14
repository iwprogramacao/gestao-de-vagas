package br.com.rocketseat.main.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

public class TestUtils {

    public static String objectToJSON(Object object) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            System.out.println(objectMapper.writeValueAsString(object));
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateToken(UUID companyId, String secret) {
        // Gerador do algorítmo do JWT
        Algorithm algorithm = Algorithm.HMAC256(secret); // Secret do JWT
        var expires_in = Instant.now() // Adiciona expiração do token
                                .plus(Duration.ofDays(2));
        var token = JWT.create()
                       .withIssuer("W.A.S.D") // Quem está emitindo (geralmente o nome da empresa)
                       .withSubject(companyId.toString())
                       .withExpiresAt(expires_in)
                       .withClaim("roles", Arrays.asList("COMPANY"))
                       .sign(algorithm); // Algoritmo de criação

        return token;
    }
}
