package br.com.rocketseat.main.security;

import br.com.rocketseat.main.providers.JWTCandidateProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityCandidateFilter extends OncePerRequestFilter {

    @Autowired
    private JWTCandidateProvider jwtCandidateProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
//        SecurityContextHolder.getContext()
//                             .setAuthentication(null);

        String header = request.getHeader("Authorization");

        if (request.getRequestURI()
                   .startsWith("/candidate")) {
            if (header != null) {

                var tokenDecoded = this.jwtCandidateProvider.validateToken(header);

                if (tokenDecoded == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                request.setAttribute("candidate_id", tokenDecoded.getSubject());
                var roles = tokenDecoded.getClaim("roles")
                                        .asList(String.class);

                var grants = roles.stream()
                                  .map((String role) -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                                  .toList();
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(tokenDecoded.getSubject(),
                                null,
                                grants);

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);
    }
}
