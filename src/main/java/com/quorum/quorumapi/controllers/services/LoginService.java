package com.quorum.quorumapi.controllers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.quorum.quorumapi.controllers.LoginData;
import com.quorum.quorumapi.models.User;
// import com.quorum.quorumapi.errors.UsernameNotFoundError;
import com.quorum.quorumapi.repositories.UsersRepository;
import com.quorum.quorumapi.security.services.TokenService;

@Service
public class LoginService {
  @Autowired
  UsersRepository repository;
  @Autowired
  AuthenticationManager authManager;
  @Autowired
  TokenService tokenService;

  public String log(LoginData data) {
    var authToken = new UsernamePasswordAuthenticationToken(data.email(),
        data.password());
    var auth = authManager.authenticate(authToken);

    return tokenService.generate((User) auth.getPrincipal());
  }

}
