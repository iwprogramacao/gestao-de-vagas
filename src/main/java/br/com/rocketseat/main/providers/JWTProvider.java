package br.com.rocketseat.main.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTProvider {
    @Value("${security.jwt.secret}") // Busca das variáveis de ambiente o valor configurado
    private String jwtSecret;

    public DecodedJWT validateToken(String token) {
        try {
            token = token.replace("Bearer ", "");

            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

            var tokenDecoed = JWT.require(algorithm)
                             .build()
                             .verify(token);

            return tokenDecoed;
        } catch (JWTVerificationException e) {
            e.printStackTrace();

            return null;
        }
    }
}
