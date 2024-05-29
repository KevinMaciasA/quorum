package com.quorum.quorumapi.security.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Getter
@Configuration
public class JwtConfig {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.issuer}")
  private String issuer;

  @Value("${jwt.expirationInHours}")
  private Integer expirationInHours;

  @Value("${jwt.timeOffset}")
  private Integer timeOffset;
}