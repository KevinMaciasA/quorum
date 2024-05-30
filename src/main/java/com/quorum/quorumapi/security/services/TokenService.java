package com.quorum.quorumapi.security.services;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.quorum.quorumapi.models.User;

@Service
public class TokenService {

  private final JwtConfig jwtConfig;
  private final Algorithm algorithm;

  @Autowired
  TokenService(JwtConfig jwtConfig) {
    this.jwtConfig = jwtConfig;
    this.algorithm = Algorithm.HMAC256(jwtConfig.getSecret());
  }

  public String generate(User user) {
    final var NOW = new Date();
    final var EXPIRATION_DATE = LocalDateTime.now()
        .plusHours(jwtConfig.getExpirationInHours())
        .toInstant(ZoneOffset.ofHours(jwtConfig.getTimeOffset()));

    return JWT.create()
        .withSubject(user.getUsername())
        .withIssuer(jwtConfig.getIssuer())
        .withIssuedAt(NOW)
        .withExpiresAt(EXPIRATION_DATE)
        .sign(algorithm);
  }

  public String validateTokenAndGetEmail(String token) throws JWTVerificationException {
    JWTVerifier verifier = JWT.require(algorithm)
        .withIssuer(jwtConfig.getIssuer())
        .build();
    DecodedJWT jwt = verifier.verify(token);
    return jwt.getSubject();
  }
}
