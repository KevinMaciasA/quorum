package com.quorum.quorumapi.controllers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.quorum.quorumapi.controllers.dataObjects.RegisterData;
import com.quorum.quorumapi.controllers.dataObjects.user.UserData;
import com.quorum.quorumapi.errors.EmailAlreadyUsedError;
import com.quorum.quorumapi.models.User;
import com.quorum.quorumapi.repositories.UsersRepository;

@Service
public class RegisterService {
  @Autowired
  private UsersRepository repository;
  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  public UserData save(RegisterData data) {
    var emailAlreadyUsed = repository.existsByEmail(data.email());
    if (emailAlreadyUsed)
      throw new EmailAlreadyUsedError();

    var encodedPassword = passwordEncoder.encode(data.password());

    var newUser = new User(
        data.email(), data.username(), encodedPassword);
    return repository.save(newUser).userData();
  }

}
