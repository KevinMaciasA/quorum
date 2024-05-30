package com.quorum.quorumapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.quorum.quorumapi.controllers.dataObjects.RegisterData;
import com.quorum.quorumapi.controllers.dataObjects.login.LoginData;
import com.quorum.quorumapi.controllers.dataObjects.login.LoginResponse;
import com.quorum.quorumapi.controllers.services.LoginService;
import com.quorum.quorumapi.controllers.services.RegisterService;
import com.quorum.quorumapi.models.UserData;

import jakarta.validation.Valid;

@RestController
public class AuthenticationController {
  @Autowired
  private LoginService loginService;

  @Autowired
  private RegisterService registerService;

  @PostMapping(path = "/login")
  public ResponseEntity<?> login(@RequestBody @Valid LoginData data) {
    var jwt = loginService.log(data);
    return ResponseEntity.ok(new LoginResponse(jwt));
  }

  @PostMapping(path = "/register")
  public ResponseEntity<UserData> register(@RequestBody @Valid RegisterData data, UriComponentsBuilder uri) {
    var result = registerService.save(data);
    var path = uri.path("/users/{id}").buildAndExpand(result.id()).toUri();
    return ResponseEntity.created(path).body(result);
  }
}
