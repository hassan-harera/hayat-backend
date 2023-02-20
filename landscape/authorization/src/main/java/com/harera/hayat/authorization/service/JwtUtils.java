package com.harera.hayat.authorization.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.harera.hayat.authorization.model.user.AuthUser;
import com.harera.hayat.authorization.repository.TokenRepository;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;

@Service
public class JwtUtils {

    private final String secretKey;
    private final TokenRepository tokenRepository;

    public JwtUtils(@Value("${jwt.token.secret.key}") String secretKey,
                    TokenRepository tokenRepository) {
        this.secretKey = secretKey;
        this.tokenRepository = tokenRepository;
    }

    public Long extractUserId(String token) {
        try {
            return Long.parseLong(Jwts.parser().setSigningKey(secretKey)
                            .parseClaimsJws(token).getBody().getSubject());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * User subject is user's mobile or user name. The credential that they used
     * to login
     */
    public String extractUserSubject(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody()
                        .get("id", String.class);
    }

    /**
     * User subject is user's mobile or user name. The credential that they used
     * to login
     */
    public String extractUserRole(String token) {
        return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                        .getBody().get("role");
    }

    public void validateToken(AuthUser user, String token) {
        validateJWt(token, user);
        if (!Objects.equals(tokenRepository.getUserIdForToken(token), user.getId())) {
            throw new ExpiredJwtException(null, null, "JWT is not associated to user");
        }
    }

    public void validateRefreshToken(AuthUser user, String token) {
        validateJWt(token, user);
        if (!Objects.equals(tokenRepository.getUserIdForRefreshToken(token),
                        user.getId())) {
            throw new ExpiredJwtException(null, null, "JWT is not associated to user");
        }
    }

    private void validateJWt(String token, AuthUser user) {
        Jwts.parser().setSigningKey(secretKey).parse(token);
        final String tokenSubject = extractUserSubject(token);
        if (!(Objects.equals(tokenSubject, user.getUsername())
                        || Objects.equals(tokenSubject, user.getMobile()))) {
            throw new MalformedJwtException(
                            "Username or mobile is not assotiated to this JWT");
        }
    }

}
