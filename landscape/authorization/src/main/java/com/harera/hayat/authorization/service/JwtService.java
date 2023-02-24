package com.harera.hayat.authorization.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.harera.hayat.authorization.model.user.AuthUser;
import com.harera.hayat.authorization.model.user.Role;
import com.harera.hayat.authorization.repository.TokenRepository;
import com.harera.hayat.authorization.repository.UserRepository;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

    private final String tokenExpire;
    private final String refreshTokenExpire;
    private final String secretKey;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final TokenRepository tokenRepository;

    @Autowired
    public JwtService(@Value("${jwt.token.expire}") String tokenExpire,
                    @Value("${jwt.token.refresh.expire}") String refreshTokenExpire,
                    @Value("${jwt.token.secret.key}") String secretKey,
                    UserRepository userRepository, AuthValidation ignoredAuthValidation,
                    JwtUtils jwtUtils, TokenRepository tokenRepository) {
        this.tokenExpire = tokenExpire;
        this.refreshTokenExpire = refreshTokenExpire;
        this.secretKey = secretKey;
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.tokenRepository = tokenRepository;
    }

    private Map<String, Object> getClaims(AuthUser user) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put("id", user.getId());
        claims.put("uid", user.getUid());
        claims.put("username", user.getUsername());
        claims.put("email", user.getEmail());
        claims.put("mobile", user.getMobile());
        claims.put("role", Role.GUEST);
        claims.put("authorities", user.getAuthorities().stream().map(
                GrantedAuthority::getAuthority).toArray());
        return claims;
    }

    public String generateToken(AuthUser user) {
        final String userSubject = String.valueOf(user.getId());
        final String token = createToken(userSubject, Long.valueOf(tokenExpire),
                        getClaims(user));
        tokenRepository.addToken(user.getId(), token);
        return token;
    }

    public String generateRefreshToken(AuthUser user) {
        final String token = createToken(user.getUsername(),
                        Long.valueOf(refreshTokenExpire), null);
        tokenRepository.addRefreshToken(user.getId(), token);
        return token;
    }

    private String createToken(String username, long expireInMillis,
                    Map<String, Object> claims) {
        final JwtBuilder jwtBuilder = Jwts.builder();
        if (claims != null) {
            jwtBuilder.setClaims(claims);
        }

        if (expireInMillis != -1) {
            jwtBuilder.setExpiration(
                            new Date(System.currentTimeMillis() + expireInMillis));
        }

        jwtBuilder.setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
                        .signWith(SignatureAlgorithm.HS256, secretKey);
        return jwtBuilder.compact();
    }
}
