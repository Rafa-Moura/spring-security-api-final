package br.com.rafaelmoura.spring_security_api.security.authentication;

import br.com.rafaelmoura.spring_security_api.security.userdetails.UserDetailsImpl;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;

@Service
public class JwtTokenService {

    private static final String TOKEN_SECRET = "minhachavesecreta";
    private static final String ISSUER = "nome-da-aplicacao";

    public String generateToken(UserDetailsImpl userDetails) {
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);

        Instant issuedAt = Instant.now().atZone(ZoneId.systemDefault()).toInstant();
        Instant expiresAt = issuedAt.atZone(ZoneId.systemDefault()).plusMinutes(2).toInstant();

        return JWT.create()
                .withIssuer(ISSUER)
                .withClaim("email", userDetails.getUsername())
                .withClaim("user", userDetails.getUser().getName())
                .withArrayClaim("roles", convertAuthorities(userDetails))
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withSubject(userDetails.getUsername())
                .sign(algorithm);
    }

    public String getSubjectFromToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);

        return JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build()
                .verify(token)
                .getSubject();
    }

    public static String[] convertAuthorities(UserDetailsImpl userDetails) {
        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);
    }

}
